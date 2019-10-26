package duke.logic.commands.results;

import duke.model.locations.RouteNode;
import duke.model.locations.Venue;

import java.util.ArrayList;

/**
 * Defines the command result of a command needing a map.
 */
public class CommandResultMap extends CommandResult implements Routeable {
    private ArrayList<Venue> route;

    /**
     * Constructs a basic CommandResultMap object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultMap(String message) {
        this.message = message;
    }

    @Override
    public ArrayList<Venue> getRoute() {
        return route;
    }

    @Override
    public void setRoute(ArrayList<Venue> route) {
        this.route = route;
    }
}
