package duke.logic.conversations;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.ParserUtil;

public class FreeTimeConversation extends Conversation {
    private static final String command = "findtime";
    private String duration;

    public FreeTimeConversation() {
        super();
        prompt = MessagesPrompt.FREETIME_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            duration = input;
            buildResult();
        }

        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        if (duration != null) {
            result = command + " " + duration;
        } else {
            attempts++;
        }
    }
}
