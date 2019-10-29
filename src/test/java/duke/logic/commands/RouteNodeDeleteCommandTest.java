package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.parsers.Parser;
import duke.model.Model;

import duke.model.locations.BusStop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteNodeDeleteCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        BusStop newNode = new BusStop("66211", "", "", 0, 0);
        newNode.fetchData(model);
        model.getRoutes().get(0).addNode(newNode);
        assertTrue(model.getRoutes().get(0).getNode(0) instanceof BusStop);

        RouteNodeDeleteCommand routeNodeDeleteCommand =
                (RouteNodeDeleteCommand) Parser.parseComplexCommand("routeNodeDelete 1 1");
        routeNodeDeleteCommand.execute(model);

        assertTrue(model.getRoutes().get(0).getNumNodes() == 0);

        //negative test for deleting non-existant route node
        RouteNodeDeleteCommand routeNodeDeleteCommand3 =
                (RouteNodeDeleteCommand) Parser.parseComplexCommand("routeNodeDelete 1 1");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeNodeDeleteCommand3.execute(model);
        });

        RouteDeleteCommand routeDeleteCommand =
                (RouteDeleteCommand) Parser.parseComplexCommand("routeDelete 1");
        routeDeleteCommand.execute(model);

        //negative test for deleting non-existant route node in non-existant route
        RouteNodeDeleteCommand routeNodeDeleteCommand4 =
                (RouteNodeDeleteCommand) Parser.parseComplexCommand("routeNodeDelete 1 1");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeNodeDeleteCommand4.execute(model);
        });
    }
}
