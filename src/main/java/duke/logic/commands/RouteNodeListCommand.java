package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

public class RouteNodeListCommand extends Command {
    private int indexRoute;
    private int indexNode;

    /**
     * Creates a new RouteNodeListCommand with the given parameters.
     *
     * @param indexRoute The index of the Route in RouteList.
     * @param indexNode The index of the Route Node in Route.
     */
    public RouteNodeListCommand(int indexRoute, int indexNode) {
        this.indexRoute = indexRoute;
        this.indexNode = indexNode;
    }

    /**
     * Executes this command on the given Route and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        try {
            return new CommandResultText(model.getRoutes().get(indexRoute - 1).getNode(indexNode - 1));
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }
}