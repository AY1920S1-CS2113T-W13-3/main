package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when a RouteNodeDelete command is entered.
 */
public class RouteNodeDeleteConversation extends Conversation {
    private static final String command = "routeNodeDelete";
    private String routeIndex;
    private String nodeIndex;

    /**
     * Initialises the Conversation object.
     */
    public RouteNodeDeleteConversation() {
        super();
        prompt = Messages.PROMPT_ROUTENODE_DELETE_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        switch (state) {
            case 1:
                if (isIntInput(input)) {
                    routeIndex = input;
                    prompt = Messages.PROMPT_ROUTENODE_DELETE_NODEINDEX;
                    state++;
                }

                break;
            case 2:
                if (isIntInput(input)) {
                    nodeIndex = input;
                    prompt = Messages.PROMPT_ROUTENODE_DELETE_SUCCESS;
                    state++;
                }

                buildResult();
                setFinished(true);
                break;
            default:
                prompt = Messages.PROMPT_ERROR;
                break;
        }
        tryCancelConversation(input);
    }

    /**
     * Gets result of prompt.
     *
     * @return result The result.
     */
    @Override
    protected void buildResult() {
        result = command + " " + routeIndex + " " + nodeIndex;
    }
}
