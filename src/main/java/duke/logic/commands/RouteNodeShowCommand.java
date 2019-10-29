package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.ApiFailedRequestException;
import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.api.ApiParser;
import duke.logic.commands.results.CommandResultImage;
import duke.model.Model;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import duke.model.locations.Venue;
import duke.model.transports.Route;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Creates a StaticMap image of a Route for visualization.
 */
public class RouteNodeShowCommand extends Command {
    private static final String DIMENSIONS = "512";
    private static final String ZOOM_LEVEL = "14";
    private static final String RED_VALUE_OTHER = "255";
    private static final String GREEN_VALUE_OTHER = "122";
    private static final String BLUE_VALUE_OTHER = "0";
    private static final String RED_VALUE_QUERY = "122";
    private static final String GREEN_VALUE_QUERY = "255";
    private static final String BLUE_VALUE_QUERY = "0";
    private static final String LINE_WIDTH = "2";
    private int indexRoute;
    private int indexNode;

    /**
     * Creates a new RouteNodeShowCommand with the given parameters.
     *
     * @param indexRoute The index of the Route in RouteList.
     * @param indexNode indexNode The index of the Node in RouteList.
     */
    public RouteNodeShowCommand(int indexRoute, int indexNode) {
        this.indexRoute = indexRoute;
        this.indexNode = indexNode;
    }

    /**
     * Executes this command on the given Route and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultRouteMap.
     * @throws QueryOutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultImage execute(Model model) throws QueryOutOfBoundsException,
            ApiNullRequestException, ApiTimeoutException, ApiFailedRequestException {
        Route route = model.getRoutes().get(indexRoute);
        RouteNode node = model.getRoutes().get(indexRoute).getNode(indexNode);
        String rgb = RED_VALUE_OTHER + "," + GREEN_VALUE_OTHER + "," + BLUE_VALUE_OTHER;

        String param;
        if (node instanceof BusStop) {
            param = ((BusStop) node).getBusCode();
        } else {
            param = node.getAddress();
        }

        Venue query = ApiParser.getLocationSearch(param);
        ArrayList<String> points = generateOtherPoints(route, node);

        Image image = ApiParser.getStaticMap(ApiParser.generateStaticMapParams(DIMENSIONS, DIMENSIONS, ZOOM_LEVEL,
                String.valueOf(query.getLatitude()), String.valueOf(query.getLongitude()), "",
                generateLineParam(points, rgb), generatePointParam(route, node)));

        return new CommandResultImage(Messages.PROMPT_ROUTE_SELECTOR_DISPLAY + node.getDisplayInfo(),image);
    }

    /**
     * Generates the other points in the Route as an ArrayList.
     *
     * @param route The Route object.
     * @param query The original RouteNode being queried.
     * @return points The ArrayList of points.
     */
    public ArrayList<String> generateOtherPoints(Route route, RouteNode query) {
        ArrayList<String> points = new ArrayList<>();
        for (RouteNode node: route.getNodes()) {
            if (!node.equals(query)) {
                points.add(node.getLatitude() + "," + node.getLongitude());
            }
        }

        return points;
    }

    /**
     * Generates the line parameters for the StaticMap request/
     *
     * @param points The ArrayList of points.
     * @param rgb The RGB value.
     * @return The line parameters.
     */
    public String generateLineParam(ArrayList<String> points, String rgb)  {
        return ApiParser.generateStaticMapLines(points, rgb, LINE_WIDTH);
    }

    /**
     * Generates the point parameters of a Route.
     *
     * @param route The Route object.
     * @param query The RouteNode being shown.
     * @return result The point parameters.
     */
    public String generatePointParam(Route route, RouteNode query) {
        String result = "";
        for (RouteNode node: route.getNodes()) {
            if (!node.equals(query) && isWithinDistance(node, query)) {
                result += ApiParser.createStaticMapPoint(String.valueOf(node.getLatitude()),
                        String.valueOf(node.getLongitude()), RED_VALUE_OTHER, GREEN_VALUE_OTHER, BLUE_VALUE_OTHER,
                        node.getAddress()) + "|";
            } else {
                result += ApiParser.createStaticMapPoint(String.valueOf(node.getLatitude()),
                        String.valueOf(node.getLongitude()), RED_VALUE_QUERY, GREEN_VALUE_QUERY, BLUE_VALUE_QUERY,
                        node.getAddress()) + "|";
            }
        }
        result = result.substring(0, result.length() - 1);

        return result;
    }

    /**
     * Checks if a node is close enough to appear in the StaticMap image of the query.
     *
     * @param query The node being checked.
     * @param node The node in the center of the image.
     * @return Whether the node is close enough to be added.
     */
    private boolean isWithinDistance(RouteNode query, RouteNode node) {
        if ((node.getLatitude() - query.getLatitude()) < 0.04 &&
                (node.getLongitude() - query.getLongitude()) < 0.04) {
            return true;
        } else {
            return false;
        }
    }
}
