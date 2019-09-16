package duke.parsers;

import duke.commons.DukeDateTimeParseException;
import duke.commons.DukeException;
import duke.commons.MessageUtil;
import duke.tasks.*;

/**
 * Parser for utility functions.
 */
public class ParserUtil {
    /**
     * Parses the userInput and return a new to-do constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new to-do object.
     */
    protected static Todo createTodo(String userInput) throws DukeException {
        String description = userInput.substring("todo".length()).strip();
        if (description.isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        return new Todo(description);
    }

    /**
     * Parses the userInput and return a new deadline constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new deadline object.
     */
    protected static Deadline createDeadline(String userInput) throws DukeException {
        String[] deadlineDetails = userInput.substring("deadline".length()).strip().split("/by");
        if (deadlineDetails.length != 2 || deadlineDetails[1] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (deadlineDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        try {
            return new Deadline(deadlineDetails[0].strip(),
                    ParserTimeUtil.parseStringToDate(deadlineDetails[1].strip()));
        } catch (DukeDateTimeParseException e) {
            return new Deadline(deadlineDetails[0].strip(), deadlineDetails[1].strip());
        }
    }

    /**
     * Parses the userInput and return a new event constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new event object.
     */
    protected static Event createEvent(String userInput) throws DukeException {
        String[] eventDetails = userInput.substring("event".length()).strip().split("/at");
        if (eventDetails.length != 2 || eventDetails[1] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (eventDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        try {
            return new Event(eventDetails[0].strip(), ParserTimeUtil.parseStringToDate(eventDetails[1].strip()));
        } catch (DukeDateTimeParseException e) {
            return new Event(eventDetails[0].strip(), eventDetails[1].strip());
        }
    }

    protected static Fixed createFixed(String userInput) throws  DukeException {
        String[] fixedDetails = userInput.substring("fixed".length()).strip().split("needs");
        if (fixedDetails.length != 2 || fixedDetails[1] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (fixedDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        String[] timeDetails = fixedDetails[1].strip().split("hours");
        int hour = 0;
        int min = 0;
        if (timeDetails.length == 2) {
            hour = Integer.parseInt( timeDetails[0].strip() );
            min = Integer.parseInt( timeDetails[1].replaceAll("mins","").strip() );
        } else if (timeDetails[0].contains("mins")){
            min = Integer.parseInt( timeDetails[0].replaceAll("mins" , "").strip() );
        } else {
            hour = Integer.parseInt( timeDetails[0].strip() );
        }
        return new Fixed(fixedDetails[0] , hour , min );
    }

    /**
     * Parses the userInput and return an index extracted from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    protected static int getIndex(String userInput) throws DukeException {
        try {
            int index = Integer.parseInt(userInput.replaceAll("\\D+", ""));
            return index - 1;
        } catch (NumberFormatException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
    }


}
