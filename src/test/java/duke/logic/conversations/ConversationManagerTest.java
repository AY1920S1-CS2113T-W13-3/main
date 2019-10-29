package duke.logic.conversations;

import duke.commons.exceptions.DukeException;
import duke.logic.RouteManager;
import duke.logic.commands.DeleteCommand;
import duke.logic.commands.PromptCommand;

import duke.model.lists.RouteList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversationManagerTest {

    @Test
    void converse() throws DukeException {
        ConversationManager conversationManager = new ConversationManager(new RouteManager(new RouteList()));
        conversationManager.converse("delete");
        assertTrue(conversationManager.getCommand() instanceof PromptCommand);
        conversationManager.converse("Travel the Seven seas");
        assertTrue(conversationManager.getCommand() instanceof PromptCommand);
        conversationManager.converse("1");
        assertTrue(conversationManager.getCommand() instanceof DeleteCommand);
    }

    @Test
    void clearContext() throws DukeException {
        ConversationManager conversationManager = new ConversationManager(new RouteManager(new RouteList()));
        conversationManager.converse("done");
        assertTrue(conversationManager.getCommand() instanceof PromptCommand);
        conversationManager.clearContext();
        assertThrows(NullPointerException.class, conversationManager::getCommand);
    }

    @Test
    void getCommand() throws DukeException {
        ConversationManager conversationManager = new ConversationManager(new RouteManager(new RouteList()));
        conversationManager.converse("delete");
        assertTrue(conversationManager.getCommand() instanceof PromptCommand);
    }
}
