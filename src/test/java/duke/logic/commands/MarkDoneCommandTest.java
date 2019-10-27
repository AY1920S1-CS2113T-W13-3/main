package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.model.Event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;


class MarkDoneCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        Event event = new Event("NUS", LocalDateTime.now(), LocalDateTime.now());
        AddCommand addCommand = new AddCommand(event);
        addCommand.execute(model);
        MarkDoneCommand markDoneCommand = new MarkDoneCommand(0);
        markDoneCommand.execute(model);
        assertTrue(model.getEvents().get(0).isDone());
    }
}
