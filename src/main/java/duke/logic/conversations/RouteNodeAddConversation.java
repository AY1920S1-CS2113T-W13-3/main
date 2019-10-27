package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when a RouteNodeAddCommand is entered.
 */
public class RouteNodeAddConversation extends Conversation {
    private static final String command = "routeNodeAdd";
    private String routeIndex;
    private String nodeIndex;
    private String name;
    private String constraint;

    /**
     * Initialises the Conversation object.
     */
    public RouteNodeAddConversation() {
        super();
        prompt = Messages.PROMPT_ROUTENODE_ADD_STARTER;
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
                    prompt = Messages.PROMPT_ROUTENODE_ADD_NODEINDEX;
                    state++;
                }

                break;
            case 2:
                if (isIntInput(input)) {
                    nodeIndex = input;
                    prompt = Messages.PROMPT_ROUTENODE_ADD_TYPE;
                    state++;
                }

                break;
            case 3:
                if (isConstraint(input)) {
                    constraint = input;
                    prompt = Messages.PROMPT_ROUTENODE_ADD_INPUT;
                    state++;
                }
                break;
            case 4:
                name = input;
                prompt = Messages.PROMPT_ROUTENODE_ADD_SUCCESS;

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
        if (!nodeIndex.equals("0")) {
            result = command + " " + routeIndex + " " + nodeIndex + " at " + name + " by " + constraint;
        } else {
            result = command + " " + routeIndex + " at " + name + " by " + constraint;
        }
    }
}
