package sgtravel.logic.parsers.commandparsers;

import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.RouteAddCommand;

/**
 * Parses the user inputs into suitable format for RouteAddCommand.
 */
public class RouteAddParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    /**
     * Constructs the RouteAddParser.
     *
     * @param input The user input.
     */
    public RouteAddParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteAddCommand object.
     *
     * @return The RouteAddCommand object.
     */
    @Override
    public Command parse() {
        String[] details = input.split("desc", TWO);
        if (details.length == TWO) {
            return new RouteAddCommand(details[ZERO], details[ONE]);
        } else {
            return new RouteAddCommand(details[ZERO], "");
        }
    }
}
