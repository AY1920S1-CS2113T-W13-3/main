package duke.logic.conversations;

import duke.commons.Messages;
import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeDateTimeParseException;
import duke.logic.parsers.ParserTimeUtil;

public class SearchConversation extends Conversation {
    private static final String command = "search";
    private String location;

    public SearchConversation() {
        super();
        prompt = MessagesPrompt.SEARCH_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        prompt = MessagesPrompt.SEARCH_PROMPT_SUCCESS;
        location = input.replace(" ", "+");
        buildResult();
        setFinished(true);
    }

    @Override
    protected void buildResult() {
        result = command + " " + location;
    }
}
