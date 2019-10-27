package duke.logic;

import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.QueryFailedException;
import duke.logic.api.ApiConstraintParser;
import duke.model.Model;
import duke.model.locations.BusStop;
import duke.model.locations.CustomNode;
import duke.model.locations.RouteNode;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;
import duke.model.transports.BusService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * Defines an algorithm to find a path between 2 Venues.
 */
public class PathFinder {
    private CreateMap map;
    private HashSet<BusStop> visited;
    private HashMap<String, String> path;
    private boolean found = false;

    /**
     * Initialise Pathfinder object.
     *
     */
    public PathFinder(CreateMap map) {
        this.map = map;
        this.visited = new HashSet<>();
        this.path = new HashMap<>();
    }


    /**
     * Find path between start and end.
     *
     * @param start starting location.
     * @param end ending location.
     * @return path.
     */
    public ArrayList<Venue> execute(Venue start, Venue end, Constraint constraint) {
        found = false;
        switch (constraint) {
        case BUS:
            return findBusRoute(start, end);
        case MRT:
            return findTrainRoute(start, end);
        default:
            return findMixedRoute(start, end);
        }
    }

    private ArrayList<Venue> findMixedRoute(Venue start, Venue end) {
        Venue startTransport = ApiConstraintParser.getNearestTransport(start, this.map);
        Venue endTransport = ApiConstraintParser.getNearestTransport(end, this.map);
        ArrayList<Venue> ans = new ArrayList<>();

        if (startTransport instanceof TrainStation && endTransport instanceof TrainStation) {
            return findTrainRoute(start, end);
        }

        if (startTransport instanceof BusStop && endTransport instanceof TrainStation) {
            TrainStation middleTrain = ApiConstraintParser.getNearestTrainStation(start, this.map.getTrainMap());
            BusStop middleBus = ApiConstraintParser.getNearestBusStop(middleTrain, this.map.getBusStopMap());

            ans = findBusRoute(start, middleBus);
            ans.addAll(Objects.requireNonNull(findTrainRoute(middleTrain, end)));
        }

        if (startTransport instanceof TrainStation && endTransport instanceof BusStop) {
            TrainStation middleTrain = ApiConstraintParser.getNearestTrainStation(end, this.map.getTrainMap());
            BusStop middleBus = ApiConstraintParser.getNearestBusStop(middleTrain, this.map.getBusStopMap());

            ans = findTrainRoute(start, middleTrain);
            ans.addAll(Objects.requireNonNull(findBusRoute(middleBus, end)));
        }

        if (startTransport instanceof BusStop && endTransport instanceof BusStop) {
            TrainStation startTrain = ApiConstraintParser.getNearestTrainStation(start, this.map.getTrainMap());
            TrainStation endTrain = ApiConstraintParser.getNearestTrainStation(end, this.map.getTrainMap());
            BusStop startMiddleBus = ApiConstraintParser.getNearestBusStop(startTrain, this.map.getBusStopMap());
            BusStop endMiddleBus = ApiConstraintParser.getNearestBusStop(endTrain, this.map.getBusStopMap());

            ans = findBusRoute(start, startMiddleBus);
            ans.addAll(Objects.requireNonNull(findTrainRoute(startTrain, endTrain)));
            ans.addAll(Objects.requireNonNull(findBusRoute(endMiddleBus, end)));
        }

        return ans;
    }

    private ArrayList<Venue> findTrainRoute(Venue start, Venue end) {
        this.found = false;
        TrainStation startTrainStation = ApiConstraintParser.getNearestTrainStation(start, this.map.getTrainMap());
        TrainStation endTrainStation = ApiConstraintParser.getNearestTrainStation(end, this.map.getTrainMap());
        ArrayList<Venue> path = new ArrayList<>();

        if (isSameLocation(startTrainStation, endTrainStation)) {
            path.add(start);
            path.add(end);
            return path;
        }

        if (!isSameLocation(start, startTrainStation)) {
            path.add(start);
        }
        path.add(startTrainStation);
        if (!onSameLine(startTrainStation, endTrainStation)) {
            ArrayList<TrainStation> curTrainLine;
            for (String line : startTrainStation.getTrainCodes()) {
                curTrainLine = this.map.getTrainLine(line.substring(0,2));
                assert curTrainLine != null : "Train Code does not exist";
                for (TrainStation trainStation : curTrainLine) {
                    if (onSameLine(trainStation, endTrainStation)) {
                        path.add(trainStation);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        } else {
            found = true;
        }


        path.add(endTrainStation);

        if (!isSameLocation(end, endTrainStation)) {
            path.add(end);
        }

        if (found) {
            return path;
        } else {
            return null;
        }

    }

    private boolean isSameLocation(Venue start, Venue end) {
        return start.equals(end);
    }

    private boolean onSameLine(TrainStation cur, TrainStation endTrainStation) {
        for (String code : cur.getTrainCodes()) {
            for (String code2 : endTrainStation.getTrainCodes()) {
                if (code2.contains(code.substring(0,2))) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<Venue> findBusRoute(Venue start, Venue end) {
        this.found = false;
        BusStop startBusStop = ApiConstraintParser.getNearestBusStop(start, this.map.getBusStopMap());
        BusStop endBusStop = ApiConstraintParser.getNearestBusStop(end, this.map.getBusStopMap());
        ArrayList<Venue> ans = new ArrayList<>();

        if (isSameLocation(startBusStop, endBusStop)) {
            ans.add(start);
            ans.add(end);
            return ans;
        }

        BusStop cur = startBusStop;
        int depthLimit = 0;

        while (!found && depthLimit < 3) {
            this.visited.clear();
            this.path.clear();
            depthFirstSearch(cur, endBusStop, depthLimit);
            depthLimit += 1;
        }

        if (!this.found) {
            return null;
        } else {
            cur = endBusStop;
            if (!isSameLocation(end, endBusStop)) {
                ans.add(end);
            }
            while (!cur.getBusCode().equals(startBusStop.getBusCode())) {
                ans.add(cur);
                cur = this.map.getBusStopMap().get(path.get(cur.getBusCode()));
            }
            ans.add(cur);
            if (!isSameLocation(start, startBusStop)) {
                ans.add(start);
            }
            return ans;
        }
    }

    private void depthFirstSearch(BusStop cur, BusStop endBusStop, int depthLimit) {
        if (depthLimit == 0 || this.visited.contains(cur)) {
            return;
        }

        this.visited.add(cur);

        for (String bus : cur.getBuses()) { //loop through all bus in bus stop
            int direction;
            if (this.map.getBusMap().get(bus).getDirection(1).contains(cur.getBusCode())) {
                direction = 1;
            } else {
                direction = 2;
            }

            for (String busCode : this.map.getBusMap().get(bus).getDirection(direction)) { // depth search the bus route

                if (this.found) {
                    break;
                }

                if (busCode.equals(cur.getBusCode())) {
                    continue;
                }

                path.put(busCode, cur.getBusCode());
                if (haveSameBus(this.map.getBusStopMap().get(busCode), endBusStop)) {
                    path.put(endBusStop.getBusCode(), busCode);
                    this.found = true;
                    return;
                } else {
                    depthFirstSearch(this.map.getBusStopMap().get(busCode), endBusStop, depthLimit - 1);
                }
            }
        }
    }

    private boolean haveSameBus(BusStop cur, BusStop endBusStop) {
        for (String bus : cur.getBuses()) {
            if (endBusStop.getBuses().contains(bus)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a custom RouteNode from a venue.
     *
     * @param venue The venue.
     * @return The custom RouteNode created.
     */
    public static RouteNode generateCustomRouteNode(Venue venue) {
        return new CustomNode(venue.getAddress(), "", venue.getLatitude(), venue.getLongitude());
    }

    /**
     * Generates an ArrayList of Venues between 2 Venues.
     *
     * @param startVenue The starting Venue.
     * @param endVenue The ending Venue.
     * @param model The model object containing information about the user.
     * @return result The ArrayList of Venues.
     * @throws QueryFailedException If a TrainStation cannot be queried.
     */
    public static ArrayList<Venue> generateInbetweenNodes(Venue startVenue, Venue endVenue, Model model)
            throws QueryFailedException {
        ArrayList<Venue> result = new ArrayList<>();

        if (startVenue instanceof BusStop && endVenue instanceof BusStop) {
            result = generateInbetweenBusRoutes(startVenue, endVenue, model);
        } else if (startVenue instanceof TrainStation && endVenue instanceof TrainStation) {
            result = generateInbetweenTrainRoutes((TrainStation) startVenue, (TrainStation) endVenue, model);
        }

        return result;
    }

    /**
     * Generates an ArrayList of BusStops between 2 Bus Stops.
     *
     * @param startVenue The starting Venue.
     * @param endVenue The ending Venue.
     * @param model The model object containing information about the user.
     * @return result The ArrayList of BusStops.
     */
    public static ArrayList<Venue> generateInbetweenBusRoutes(Venue startVenue, Venue endVenue, Model model)
            throws QueryFailedException {
        ArrayList<Venue> result = new ArrayList<>();
        HashMap<String, BusService> busMap = model.getMap().getBusMap();
        boolean isGenerated = false;

        for (String busNumber: ((BusStop) startVenue).getBuses()) {
            if (!isGenerated) {
                BusService bus = busMap.get(busNumber);
                ArrayList<String> busCodes = bus.getDirection(1);
                ArrayList<Venue> tempRoute = new ArrayList<>();
                boolean isStartNodeFound = false;

                //search forward direction
                for (String busCode : busCodes) {
                    if (((BusStop) endVenue).getBusCode().equals(busCode) && !isStartNodeFound) {
                        break;
                    }

                    if (((BusStop) endVenue).getBusCode().equals(busCode) && isStartNodeFound) {
                        result = tempRoute;
                        isGenerated = true;
                        break;
                    }

                    if (isStartNodeFound) {
                        BusStop node = new BusStop(busCode, "", "", 0, 0);
                        tempRoute.add(node);
                    }

                    if (((BusStop) startVenue).getBusCode().equals(busCode)) {
                        BusStop node = new BusStop(busCode, "", "", 0, 0);
                        node.fetchData(model);
                        tempRoute.add(new CustomNode("Bus Service " + busNumber, "", node.getLatitude(),
                                node.getLongitude()));
                        isStartNodeFound = true;
                    }
                }

                if (!isGenerated) {
                    //search backward direction
                    Collections.reverse(busCodes);
                    for (String busCode : busCodes) {
                        if (((BusStop) endVenue).getBusCode().equals(busCode) && isStartNodeFound) {
                            result = tempRoute;
                            isGenerated = true;
                            break;
                        }

                        if (isStartNodeFound) {
                            BusStop node = new BusStop(busCode, "", "", 0, 0);
                            tempRoute.add(node);
                        }

                        if (((BusStop) startVenue).getBusCode().equals(busCode)) {
                            BusStop node = new BusStop(busCode, "", "", 0, 0);
                            node.fetchData(model);
                            tempRoute.add(new CustomNode("Bus Service " + busNumber, "", node.getLatitude(),
                                    node.getLongitude()));
                            isStartNodeFound = true;
                        }
                    }
                }

            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Generates an ArrayList of TrainStations between 2 Bus Stops.
     *
     * @param startVenue The starting Venue.
     * @param endVenue The ending Venue.
     * @param model The model object containing information about the user.
     * @return result The ArrayList of TrainStations.
     */
    public static ArrayList<Venue> generateInbetweenTrainRoutes(TrainStation startVenue, TrainStation endVenue,
                                                                Model model) throws QueryFailedException {
        ArrayList<Venue> result = new ArrayList<>();
        TrainStation start = model.getMap().getTrainStation(startVenue.getDescription());
        ArrayList<String> trainCodes = start.getTrainCodes();
        boolean isGenerated = false;

        for (String trainCode: trainCodes) {

            if (!isGenerated) {
                ArrayList<TrainStation> trainLine = model.getMap().getTrainLine(trainCode.substring(0, 2));
                ArrayList<Venue> tempRoute = new ArrayList<>();
                boolean isStartNodeFound = false;

                //search forward direction
                for (TrainStation trainStation : trainLine) {
                    if (endVenue.getDescription().equals(trainStation.getDescription()) && !isStartNodeFound) {
                        break;
                    }

                    if (endVenue.getDescription().equals(trainStation.getDescription()) && isStartNodeFound) {
                        result = tempRoute;
                        isGenerated = true;
                        break;
                    }

                    if (isStartNodeFound) {
                        tempRoute.add(trainStation);
                    }

                    if (startVenue.getDescription().equals(trainStation.getDescription())) {
                        tempRoute.add(new CustomNode(trainCode + " Line", "", trainStation.getLatitude(),
                                trainStation.getLongitude()));
                        isStartNodeFound = true;
                    }
                }

                if (!isGenerated) {
                    //search backward direction
                    Collections.reverse(trainLine);
                    for (TrainStation trainStation : trainLine) {
                        if (endVenue.getDescription().equals(trainStation.getDescription()) && isStartNodeFound) {
                            result = tempRoute;
                            isGenerated = true;
                            break;
                        }

                        if (isStartNodeFound) {
                            tempRoute.add(trainStation);
                        }

                        if (startVenue.getDescription().equals(trainStation.getDescription())) {
                            tempRoute.add(new CustomNode(trainCode + " Line", "", trainStation.getLatitude(),
                                    trainStation.getLongitude()));
                            isStartNodeFound = true;
                        }
                    }
                }

            } else {
                break;
            }
        }
        return result;
    }
}
