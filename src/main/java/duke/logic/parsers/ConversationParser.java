package duke.logic.parsers;

import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.conversations.Conversation;
import duke.logic.conversations.DeleteConversation;
import duke.logic.conversations.FindConversation;
import duke.logic.conversations.FindPathConversation;
import duke.logic.conversations.GetBusStopConversation;
import duke.logic.conversations.MarkDoneConversation;
import duke.logic.conversations.RouteAddConversation;
import duke.logic.conversations.RouteDeleteConversation;
import duke.logic.conversations.RouteEditConversation;
import duke.logic.conversations.SearchConversation;
import duke.logic.conversations.SetupProfileConversation;

/**
 * Parser for conversations. Selects conversation based on user input.
 */
public class ConversationParser {
    /**
     * Parses the input and returns a Conversation object.
     * @param input The user input from Ui.
     * @return A conversation object.
     * @throws DukeUnknownCommandException If input is undefined.
     */
    public static Conversation parse(String input) throws DukeUnknownCommandException {
        switch (input) {
        case "done":
            return new MarkDoneConversation();
        case "delete":
            return new DeleteConversation();
        case "busStop":
            return new GetBusStopConversation();
        case "findPath":
            return new FindPathConversation();
        case "find":
            return new FindConversation();
        case "search":
            return new SearchConversation();
        case "profile":
            return new SetupProfileConversation();
        case "routeDelete":
            return new RouteDeleteConversation();
        case "routeAdd":
            return new RouteAddConversation();
        case "routeEdit":
            return new RouteEditConversation();
        default:
            throw new DukeUnknownCommandException();
        }
    }
}
