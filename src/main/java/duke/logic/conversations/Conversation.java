package duke.logic.conversations;

import duke.commons.Messages;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.InputNotIntException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.parsers.ParserTimeUtil;
import duke.logic.parsers.ParserUtil;

/**
 * Abstract class representing an individual Conversation.
 */
public abstract class Conversation {
    protected String result;
    protected String prompt;
    protected int state;
    private boolean isFinished;
    protected int attempts;
    protected static final int ATTEMPTS_LIMIT = 3;

    /**
     * Initialises the Conversation object.
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
     * @return result The result.
     */
    public String getResult() {
        assert (result != null) : "result should not be null";
        return result;
    }

    /**
     * Checks if input is int.
     *
     * @param input The input.
     * @return If the input is int.
     */
    protected Boolean isIntInput(String input) {
        try {
            ParserUtil.getIntegerIndexInList(0, 2, input);
            return true;
        } catch (InputNotIntException | QueryOutOfBoundsException e) {
            attempts++;
            prompt = Messages.PROMPT_NOT_INT;
            return false;
        }
    }

    /**
     * Checks if input is a field of a Route.
     *
     * @param input The input.
     * @return If the input is a field of a Route.
     */
    protected boolean isRouteField(String input) {
        if ("name".equals(input) || "description".equals(input)) {
            return true;
        } else {
            attempts++;
            prompt = Messages.PROMPT_NOT_ROUTE_FIELD;
            return false;
        }
    }

    /**
     * Checks if input is a field of a RouteNode.
     *
     * @param input The input.
     * @return If the input is a field of a RouteNode.
     */
    protected boolean isRouteNodeField(String input) {
        if ("address".equals(input) || "description".equals(input) || "type".equals(input) || "latitude".equals(input)
                || "longitude".equals(input)) {
            return true;
        } else {
            attempts++;
            prompt = Messages.PROMPT_NOT_ROUTENODE_FIELD;
            return false;
        }
    }

    /**
     * Checks if input is a Constraint enum.
     *
     * @param input The input.
     * @return If the input is a Constraint.
     */
    protected boolean isConstraint(String input) {
        try {
            Constraint.valueOf(input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            attempts++;
            prompt = Messages.ERROR_CONSTRAINT_UNKNOWN;
            return false;
        }
    }

    /**
     * Checks if input is a DateTime.
     *
     * @param input The input.
     * @return If the input is a DateTime.
     */
    protected Boolean isDateInput(String input) {
        try {
            ParserTimeUtil.parseStringToDate(input);
            return true;
        } catch (DukeDateTimeParseException e) {
            attempts++;
            prompt = Messages.PROMPT_NOT_DATE;
            return false;
        }
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

    /**
     * Returns whether the conversation is finished.
     *
     * @return Whether the conversation is finished.
     */
    public boolean isFinished() {
        return isFinished;
    }

    protected void setFinished(boolean finished) {
        isFinished = finished;
    }
}
