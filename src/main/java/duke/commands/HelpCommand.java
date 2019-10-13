package duke.commands;

import duke.storage.Storage;

/**
 * Class representing a command to show the help message.
 */
public class HelpCommand extends Command {
    private static final String MESSAGE_HELP = "Here is the list of commands:\n"
            + "Add Tasks:\n"
            + "    To Do: todo <desc>\n"
            + "    Event: event <desc> /at <time>\n"
            + "    Deadline: deadline <desc> /by <time>\n"
            + "    Recurring Task: repeat <desc> /by <time> /every <num of days>\n"
            + "    Do Within Task: within <desc> /between <time> /and <time>\n"
            + "\n"
            + "Modifying Tasks:\n"
            + "    Snooze: snooze <index> /to <date>\n"
            + "\n"
            + "Task Querying\n"
            + "    Reminder: reminder\n"
            + "    View by Date: fetch <date>\n";

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) {
        return new CommandResult(MESSAGE_HELP);
    }
}
