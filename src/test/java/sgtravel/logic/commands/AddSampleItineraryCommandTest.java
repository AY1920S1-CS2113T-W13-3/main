package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.SingaporeTravelException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddSampleItineraryCommandTest {

    @Test
    void execute() throws SingaporeTravelException, FileNotFoundException {
        ModelStub model = new ModelStub();
        LocalDateTime startDate = LocalDateTime.of(2020, 9, 9, 9, 9);
        LocalDateTime endDate = LocalDateTime.of(2020, 9, 13, 9, 9);

        String [] itineraryDetails = {"itinerary ", startDate.toString(), endDate.toString()};
        RecommendationsCommand recommendationsCommand = new RecommendationsCommand(itineraryDetails);

        recommendationsCommand.execute(model);

        AddSampleItineraryCommand addSampleItineraryCommand = new AddSampleItineraryCommand("New List");

        addSampleItineraryCommand.execute(model);

        assertTrue(model.getItineraryTable().containsKey("New List"));

    }

}
