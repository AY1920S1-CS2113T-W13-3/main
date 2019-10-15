package duke.logic.conversations;

import duke.commons.MessagesPrompt;

public class MarkDoneConversation extends Conversation {
    private static final String command = "done";
    private String index;

    public MarkDoneConversation() {
        super();
        prompt = MessagesPrompt.MARKDONE_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            index = input;
            buildResult();
        }

        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        if (index != null) {
            result = command + " " + index;
            setFinished(true);
        } else {
            attempts++;
        }
    }
}
