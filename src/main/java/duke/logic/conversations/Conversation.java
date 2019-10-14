package duke.logic.conversations;

import duke.commons.MessagesPrompt;

/**
 * Abstract class representing individual Conversation.
 */
public abstract class Conversation {
    protected String result;
    protected String prompt;
    protected int state;
    private boolean isFinished;
    protected int attempts;
    protected static final int ATTEMPTS_LIMIT = 3;

    /**
     * Initialises the Prompt object.
     */
    public Conversation() {
        isFinished = false;
        attempts = 0;
        state = 1;
        result = null;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    public abstract void execute(String input);

    /**
     * Builds the result of the conversation string.
     */
    protected abstract void buildResult();

    public String getPrompt() {
        return prompt;
    }

    /**
     * Gets result of prompt.
     *
     * @return result The result
     */
    public String getResult() {
        assert (result != null) : "result should not be null";
        return result;
    }

    /**
     * Tries to cancel the conversation.
     *
     * @param userInput The userInput from UI object.
     */
    protected void tryCancelConversation(String userInput) {
        if (attempts > ATTEMPTS_LIMIT || "cancel".equals(userInput)) {
            result = "cancel";
            isFinished = true;
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    protected void setFinished(boolean finished) {
        isFinished = finished;
    }
}