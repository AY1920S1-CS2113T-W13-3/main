package duke.logic.commands;

import duke.commons.exceptions.EmptyVenueException;
import duke.logic.EditorManager;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

public class EditorCommand extends Command {
    private static final String MESSAGE_EDITOR = "Editor mode is turned on.";
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws EmptyVenueException {
        EditorManager.activate(model.getEventList(), model.getEventVenues());
        return new CommandResultText(MESSAGE_EDITOR);
    }
}