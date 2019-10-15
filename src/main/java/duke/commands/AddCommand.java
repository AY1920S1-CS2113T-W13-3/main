package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.events.Task;

/**
 * Class representing a command to add a new task.
 */
public class AddCommand extends Command {
    private final Task task;
    private static final String MESSAGE_ADDITION = "Got it. I've added this task:\n  ";

    /**
     * Creates a new AddCommand with the given task.
     *
     * @param task The task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing task list.
     */
    @Override
    public CommandResult execute(Model model) throws DukeException {
        model.getTasks().add(task);
        model.save();
        return new CommandResult(MESSAGE_ADDITION + task);
    }
}
