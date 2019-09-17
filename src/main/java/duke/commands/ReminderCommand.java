package duke.commands;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.tasks.Task;
import duke.tasks.TaskWithDates;
import duke.tasks.UniqueTaskList;
import duke.ui.Ui;
import javafx.collections.transformation.SortedList;

import java.time.LocalDate;

public class ReminderCommand extends Command {
    private UniqueTaskList expiredTask;
    private UniqueTaskList upcomingTask;

    /**
     * Creates a new ReminderCommand.
     */
    public ReminderCommand() {
        expiredTask = new UniqueTaskList();
        upcomingTask = new UniqueTaskList();
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        SortedList<Task> tasks = storage.getTasks().getChronoList();
        for (Task t : tasks) {
            LocalDate date = ((TaskWithDates) t).getStartDate().toLocalDate();
            LocalDate now = LocalDate.now();
            if (t.isDone()) {
                continue;
            }
            if (date.compareTo(now) < 0) {
                expiredTask.add(t);
            } else {
                upcomingTask.add(t);
            }
        }
        ui.setResponse(ui.getList(upcomingTask));
    }
}
