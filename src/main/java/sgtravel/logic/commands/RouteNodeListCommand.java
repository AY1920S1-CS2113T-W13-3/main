package sgtravel.logic.commands;

import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

/**
 * Lists a given RouteNode in RouteList.
 */
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
     * @return The CommandResultText.
     * @throws OutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws OutOfBoundsException {
        try {
            return new CommandResultText(model.getRoutes().get(indexRoute).getNode(indexNode));
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }
}
