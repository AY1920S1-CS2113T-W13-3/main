package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

public class RouteListAllCommand extends Command {
    /**
     * Executes this command on the given Route List and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) {
        return new CommandResultText(model.getRoutes());
    }
}