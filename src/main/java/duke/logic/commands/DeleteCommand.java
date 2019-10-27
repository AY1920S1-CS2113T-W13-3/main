package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.model.Event;
import duke.model.Model;

/**
 * Deletes a task.
 */
public class DeleteCommand extends Command {
    private int index;
    private static final String MESSAGE_DELETE = "Alright! I've removed this task:\n  ";

    /**
     * Creates a new DeleteCommand with the given index.
     *
     * @param index The index of the task.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        try {
            Event event = model.getEvents().remove(index);
            model.save();
            return new CommandResultText(MESSAGE_DELETE + event);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.ERROR_INDEX_OUT_OF_BOUNDS);
        }
    }
}
