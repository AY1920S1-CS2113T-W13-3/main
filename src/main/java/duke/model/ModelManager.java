package duke.model;

import duke.commons.exceptions.DukeException;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.List;

public class ModelManager implements Model {
    private UniqueTaskList tasks;
    private ArrayList<BusStop> allBusStops;
    //List<TrainStation> allTrainStations;

    @Override
    public UniqueTaskList getTasks() {
        return tasks;
    }

    @Override
    public FilteredList<Task> getFilteredList() {
        return tasks.getFilteredList();
    }

    @Override
    public SortedList<Task> getChronoSortedList() {
        return tasks.getChronoList();
    }

    @Override
    public List<Venue> getLocationList() {
        //move this to UniqueTaskList
        List<Venue> locations = new ArrayList<>();
        for (Task t : tasks.getEventList()) {
            try {
                locations.add(((Event) t).getLocation());
            } catch (DukeException e) {
                //silent failure
            }
        }
        return locations;
    }

    @Override
    public FilteredList<Task> getEventList() {
        return tasks.getEventList();
    }

    @Override
    public List<BusStop> getBusStops() {
        return allBusStops;
    }

    @Override
    public List<BusService> getBusService() {
        return null;
    }

    @Override
    public List<Venue> getRecommendations() {
        List<Venue> recommendations = new ArrayList<>();
        recommendations.add(new Venue("Sentosa", 1.2454983, 103.8437327, 0, 0));
        recommendations.add(new Venue("Mandai", 1.421336, 103.802622, 0, 0));
        recommendations.add(new Venue("lck", 1.431321, 103.718253, 0, 0));
        recommendations.add(new Venue("Jurong island", 1.254945,
                103.678820, 0, 0));
        recommendations.add(new Venue("Changi Airport", 1.346703,
                103.986755, 0, 0));
        recommendations.add(new Venue("Bukit Timah", 1.327360,
                103.794509, 0, 0));
        return recommendations;
    }

    @Override
    public void save() {

    }
}