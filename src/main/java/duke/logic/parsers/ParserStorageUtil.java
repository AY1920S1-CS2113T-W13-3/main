package duke.logic.parsers;

import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.model.events.Deadline;
import duke.model.events.DoWithin;
import duke.model.events.Event;
import duke.model.events.Task;
import duke.model.events.Todo;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import duke.model.transports.BusService;

import java.time.LocalDateTime;

/**
 * Parser for Storage related operations.
 */
public class ParserStorageUtil {
    /**
     * Parses a task from String format back to task.
     *
     * @param line The String description of a task.
     * @return The corresponding task object.
     */
    public static Task createTaskFromStorage(String line) throws DukeDateTimeParseException {
        String[] taskParts = line.split("\\|");
        String type = taskParts[0].strip();
        String status = taskParts[1].strip();
        String description = taskParts[2].strip();
        Task task;
        if ("D".equals(type)) {
            try {
                task = new Deadline(description, ParserTimeUtil.parseStringToDate(taskParts[3].strip()));
            } catch (DukeDateTimeParseException e) {
                task = new Deadline(description, taskParts[3].strip());
            }
        } else if ("W".equals(type)) {
            LocalDateTime start = ParserTimeUtil.parseStringToDate(taskParts[3].strip());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(taskParts[4].strip());
            task = new DoWithin(description, start, end);
        } else if ("E".equals(type)) {
            LocalDateTime start = ParserTimeUtil.parseStringToDate(taskParts[3].strip());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(taskParts[4].strip());
            Venue location = getLocationFromStorage(taskParts);
            task = new Event(description, start, end, location);
        } else {
            task = new Todo(description);
        }
        task.setDone("true".equals(status));
        return task;
    }

    /**
     * Parses part of a task back to a Location.
     */
    private static Venue getLocationFromStorage(String[] taskParts) {
        String address = taskParts[5].strip();
        double longitude = Double.parseDouble(taskParts[7].strip());
        double latitude = Double.parseDouble(taskParts[6].strip());
        double distX = Double.parseDouble(taskParts[7].strip());
        double distY = Double.parseDouble(taskParts[8].strip());
        return new Venue(address, latitude, longitude, distX, distY);
    }

    /**
     * Parses a task from task to String format.
     *
     * @param task The task.
     * @return The corresponding String format of the task object.
     */
    public static String toStorageString(Task task) throws DukeException {
        if (task instanceof Deadline) {
            return "D | " + task.isDone() + " | " + task.getDescription() + " | " + ((Deadline) task).getDeadline();
        } else if (task instanceof Todo) {
            return  "T | " + task.isDone() + " | " + task.getDescription();
        } else if (task instanceof Event) {
            return "E | " + task.isDone() + " | " + task.getDescription() + " | " + ((Event) task).getHoliday();
        } else if (task instanceof DoWithin) {
            return "W | " + task.isDone() + " | " + task.getDescription() + " | " + ((DoWithin) task).getWithin();
        }
        throw new DukeException(Messages.CORRUPTED_TASK);
    }

    /**
     * Parses a bus stop from String format back to BusStop.
     *
     * @param line The String description of a bus stop.
     * @return The corresponding BusStop object.
     */
    public static BusStop createBusStopDataFromStorage(String line) {
        String[] busStopData = line.split("\\|");
        String busCode = busStopData[0].strip();
        String description = busStopData[1].strip();
        String address = busStopData[2].strip();
        double latitude = Double.parseDouble(busStopData[3].strip());
        double longitude = Double.parseDouble(busStopData[4].strip());
        BusStop busStop = new BusStop(busCode,description, address, latitude, longitude);
        for (int i = 5; i < busStopData.length; i++) {
            busStop.addBuses(busStopData[i].strip());
        }
        return busStop;
    }

    /**
     * Parses a bus stop from BusStop to String format.
     *
     * @param busStop The bus stop.
     * @return The corresponding String format of the BusStop object.
     */
    public static String busStopToStorageString(BusStop busStop) {
        String buses = "";
        for (String bus : busStop.getBuses()) {
            buses += " | " + bus;
        }
        return busStop.getBusCode() + " | " + busStop.getDescription() + " | " + busStop.getAddress()
                + " | " + busStop.getLatitude() + " | " + busStop.getLongitude() + buses;
    }

    /**
     * Parses a bus service from BusService to String format.
     *
     * @param busService The bus.
     * @return The corresponding String format of the BusService object.
     */
    public static String busToStorageString(BusService busService) {
        String busRoute1 = "";
        String busRoute2 = "";
        for (String busCode : busService.getDirection(1)) {
            busRoute1 += busCode + " | ";
        }
        for (String busCode : busService.getDirection(2)) {
            busRoute2 += " | " + busCode;
        }

        return  busService.getBus() + " | " + busRoute1 + "change" + busRoute2;
    }

    /**
     * Parses a bus from String format back to BusService.
     *
     * @param line The String description of a bus.
     * @return The corresponding BusService object.
     */
    public static BusService createBusFromStorage(String line) {
        String[] busData = line.split("\\|");
        boolean changedDirection = false;
        BusService busService = new BusService(busData[0].strip());
        for (int i = 1; i < busData.length; i++) {
            String buffer = busData[i].strip();
            if ("change".equals(buffer)) {
                changedDirection = true;
            }
            if (changedDirection) {
                busService.addRoute(buffer, 2);
            } else {
                busService.addRoute(buffer, 1);
            }
        }
        return busService;
    }
}
