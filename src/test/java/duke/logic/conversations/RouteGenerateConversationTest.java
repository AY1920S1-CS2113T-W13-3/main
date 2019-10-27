package duke.logic.conversations;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteGenerateConversationTest {

    @Test
    void testForBus() throws DukeException {
        String expected = "routeGenerate amk hub to nus by bus";

        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeGenerate");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("amk hub");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("nus");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-existant field
        conversationManager.converse("not_field");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.ERROR_CONSTRAINT_UNKNOWN, conversationManager.getPrompt());

        conversationManager.converse("bus");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }

    @Test
    void testForTrain() throws DukeException {
        String expected = "routeGenerate amk hub to nus by mrt";

        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeGenerate");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("amk hub");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("nus");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-existant field
        conversationManager.converse("not_field");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.ERROR_CONSTRAINT_UNKNOWN, conversationManager.getPrompt());

        conversationManager.converse("mrt");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }
}
