package duke.commands;


import duke.model.Model;

/**
 * Class representing a command to list items in a task list.
 */
public class ListCommand extends Command {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(model.getTasks());
    }
}
