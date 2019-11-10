package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.AddListFailException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoRecentItineraryException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.planning.Itinerary;

/**
 * Adds the given recommended list to users itineraries.
 */
public class AddSampleItineraryCommand extends Command {
    private String newName;

    /**
     * Creates a new AddSampleItinerary with the given newName.
     * @param newName The new name of the itinerary.
     */
    public AddSampleItineraryCommand(String newName) {
        this.newName = newName;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @throws FileNotSavedException If the data could not be saved.
     * @throws NoRecentItineraryException If there is no recent itinerary.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException, NoRecentItineraryException,
            AddListFailException {
        Itinerary itinerary = model.getRecentItinerary();
        model.confirmRecentItinerary(newName);
        model.setRecentItinerary(null);
        model.save();
        String printString = itinerary.printItinerary();
        return new CommandResultText(Messages.ADD_ITINERARY_SUCCESS + printString);
    }
}
