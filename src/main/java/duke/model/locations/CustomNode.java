package duke.model.locations;

import duke.commons.enumerations.Constraint;

import java.util.HashSet;

public class CustomNode extends RouteNode {

    /**
     * Constructs a custom RouteNode that is not a BusStop or TrainStation.
     *
     * @param address The address.
     * @param description The description.
     * @param latitude The latitude.
     * @param longitude The longitude.
     */
    public CustomNode(String address, String description, double latitude, double longitude) {
        super(Constraint.valueOf("CUSTOM"), address, description, latitude, longitude);
    }

}
