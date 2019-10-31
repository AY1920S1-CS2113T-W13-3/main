package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when a RouteEdit command is entered.
 */
public class RouteEditConversation extends Conversation {
    private static final String command = "routeEdit";
    private String index;
    private String field;
    private String newValue;

    /**
     * Initialises the Conversation object.
     */
    public RouteEditConversation() {
        super();
        prompt = Messages.PROMPT_ROUTE_EDIT_STARTER;
    }

    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            if (isIntInput(input)) {
                index = input;
                prompt = Messages.PROMPT_ROUTE_EDIT_FIELD;
                state++;
                attempts = 0;
            }

            break;
        case 2:
            if (isRouteField(input)) {
                field = input;
                prompt = Messages.PROMPT_ROUTE_EDIT_FIELD;
                state++;
                attempts = 0;
            }

            break;
        case 3:
            prompt = Messages.PROMPT_ROUTE_EDIT_SUCCESS;
            newValue = input;

            buildResult();
            setFinished(true);
            break;
        default:
            prompt = Messages.PROMPT_ERROR;
            break;
        }
        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        result = command + " " + index + " " + field + " " + newValue;
    }
}
