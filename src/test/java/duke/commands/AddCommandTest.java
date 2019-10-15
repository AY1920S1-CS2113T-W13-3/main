package duke.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.events.Task;
import duke.model.events.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();
        Task task = new Todo("homework");
        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(model);
        assertTrue(model.getTasks().contains(task));
    }
}
