package duke.commands;

import duke.model.Model;

/**
 * Class representing a command to exit duke.Duke.
 */
public class ExitCommand extends Command {
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResult execute(Model model) {
        CommandResult commandResult = new CommandResult(MESSAGE_BYE);
        commandResult.setExit(true);
        return commandResult;
    }
}
