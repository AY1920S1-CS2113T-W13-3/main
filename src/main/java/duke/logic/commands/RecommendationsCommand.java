package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;

import java.util.List;

/**
 * Recommends an itinerary based on number of trip days entered by user.
 */
public class RecommendationsCommand extends Command {
    private Itinerary itinerary;

    public RecommendationsCommand(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {

        List<Agenda> list = model.getRecommendations(itinerary.getNumberOfDays(), itinerary);

        assert (!list.isEmpty()) : "list should not be null";

        itinerary.setTasks(list);

        String result = itinerary.printItinerary();

        return new CommandResultText(result);
    }
}
