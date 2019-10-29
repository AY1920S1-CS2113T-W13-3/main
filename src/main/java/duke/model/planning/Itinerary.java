package duke.model.planning;

import duke.model.lists.AgendaList;
import duke.model.locations.Venue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

/**
 * Represents an Itinerary and its contained information.
 */
public class Itinerary extends AgendaList {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Venue hotelLocation;
    private String name;

    /**
     * Constructor to initialise new Itinerary.
     */
    public Itinerary(LocalDateTime startDate, LocalDateTime endDate, Venue hotelLocation, String name) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.hotelLocation = hotelLocation;
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Venue getHotelLocation() {
        return hotelLocation;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns number of days of the trip based on entered start and end dates.
     *
     * @return The number of days of the trip
     */

    public int getNumberOfDays() {
        LocalDateTime tempDateTime = LocalDateTime.from(startDate);
        long days = tempDateTime.until(endDate, ChronoUnit.DAYS);
        return Integer.parseInt(String.valueOf(days));
    }

    /**
     * Prints the itinerary list in entirety.
     *
     * @return The String which lists the itinerary in full
     */
    public String printItinerary() {

        int days = this.getNumberOfDays();

        StringBuilder result = new StringBuilder("Here are the list of Locations in "
                +  days + " days around " + this.getHotelLocation().getAddress() + " with name " + this.name + ": \n");
        for (int i = 0; i < this.getList().size(); i++) {
            Agenda list1 = this.getList().get(i);
            result.append("\n");
            result.append("Day ").append(list1.getNumber()).append(":").append("\n \n");
            result.append("Venues: ").append("\n");
            for (Venue venue : list1.getVenueList()) {
                result.append(venue.getAddress()).append("\n");
            }
            result.append("\n");
            result.append("Todo List: ").append("\n");
            for (Todo todo : list1.getTodoList()) {
                result.append(" - ").append(todo.getDescription()).append("\n");
            }
        }
        return result.toString();
    }
}
