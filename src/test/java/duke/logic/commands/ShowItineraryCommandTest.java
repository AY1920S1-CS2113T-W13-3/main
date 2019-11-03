package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.planning.Itinerary;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShowItineraryCommandTest {

    @Test
    void execute() throws DukeException, FileNotFoundException {
        ModelStub model = new ModelStub();
        LocalDateTime startDate = LocalDateTime.of(2020, 9, 9, 9, 9);
        LocalDateTime endDate = LocalDateTime.of(2020, 9, 13, 9, 9);

        String [] itineraryDetails = {"YEW TEE INDUSTRIAL ESTATE", startDate.toString(), endDate.toString()};

        RecommendationsCommand recommendationsCommand = new RecommendationsCommand(itineraryDetails);

        recommendationsCommand.execute(model);

        AddSampleItineraryCommand addSampleItineraryCommand = new AddSampleItineraryCommand();

        addSampleItineraryCommand.execute(model);

        ShowItineraryCommand showItineraryCommand = new ShowItineraryCommand(model.getRecentItinerary().getName());

        CommandResultText commandResultText = showItineraryCommand.execute(model);

        String result = commandResultText.getMessage();

        assertEquals(result, model.getItinerary(model.getRecentItinerary().getName()).printItinerary());

    }

}
