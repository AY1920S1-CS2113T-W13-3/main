package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.Messages;
import sgtravel.commons.enumerations.Constraint;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.RouteGenerateCommand;

/**
 * Parses the user inputs into suitable format for RouteGenerateCommand.
 */
public class RouteGenerateParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final String BUS_CONSTRAINT = "BUS";

    /**
     * Constructs the RouteGenerateParser.
     *
     * @param input The user input.
     */
    public RouteGenerateParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteGenerateCommand object.
     *
     * @return The RouteGenerateCommand object.
     * @throws ParseException If RouteGenerateCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        String[] details = input.split(" to | by ", THREE);
        if (details.length == THREE) {
            try {
                if (BUS_CONSTRAINT.equals(details[TWO].toUpperCase())) {
                    return new RouteGenerateCommand(details[ZERO], details[ONE],
                            Constraint.valueOf(details[TWO].toUpperCase()));
                } else {
                    throw new ParseException(Messages.ERROR_CONSTRAINT_UNKNOWN);
                }
            } catch (IllegalArgumentException e) {
                throw new ParseException(Messages.ERROR_CONSTRAINT_UNKNOWN);
            }
        }
        throw new ParseException(Messages.ERROR_FIELDS_EMPTY);
    }
}
