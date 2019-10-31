package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.results.CommandResult;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Sets the preference base on user input.
 */
public class ProfileSetPreferenceCommand extends Command {
    private String category;
    private Boolean setting;

    /**
     * Constructs ProfileSetPreferenceCommand object.
     * @param category Category of preference to set
     * @param setting Setting which user wish to set preference to
     */
    public ProfileSetPreferenceCommand(String category, String setting) throws ParseException {
        this.category = category.toLowerCase();
        if (setting.equalsIgnoreCase("true")) {
            this.setting = true;
        } else {
            this.setting = false;
        }
        if (!this.category.equals("sports") && !this.category.equals("entertainment")
                && !this.category.equals("arts") && !this.category.equals("lifestlye")) {
            throw new ParseException(Messages.ERROR_CATEGORY_NOT_FOUND);
        }
    }

    @Override
    public CommandResult execute(Model model) throws DukeException {
        model.getProfileCard().setPreference(category, setting);
        model.save();
        return new CommandResultText("Your preference for " + category + " is set to " + setting);
    }
}
