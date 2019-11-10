package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.lists.EventList;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Edits the EventList on SGTravel.
 */
public class EditCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private boolean canSave;
    private EventList events;

    /**
     * Creates an edit command.
     * @param canSave Boolean to decide if the edit should be saved.
     * @param events The new edited list.
     */
    public EditCommand(boolean canSave, EventList events) {
        this.canSave = canSave;
        this.events = events;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     * @throws FileNotSavedException If the data could not be saved.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException {
        if (canSave && events.isUnique()) {
            model.setEvents(events);
            model.save();
            logger.log(Level.FINE, "Event list is saved.");
            return new CommandResultText(model.getEvents());
        }
        return new CommandResultText(Messages.EDIT_FAILURE);
    }
}
