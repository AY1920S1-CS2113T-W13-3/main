package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Class representing a command to list a given Route in RouteList.
 */
public class RouteListCommand extends Command {
    private int index;

    /**
     * Creates a new RouteListCommand with the given index.
     *
     * @param index The index of the Route in RouteList.
     */
    public RouteListCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command on the given Route List and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        try {
            return new CommandResultText(model.getRoutes().get(index));
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.ERROR_INDEX_OUT_OF_BOUNDS);
        }
    }
}
