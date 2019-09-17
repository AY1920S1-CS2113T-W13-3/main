package duke.tasks;

import java.time.LocalDateTime;

public class DoWithin extends TaskWithDates {
    private LocalDateTime endDate;

    /**
     * Initializes a task to be done within two date the given description.
     *
     * @param description A description of this task.
     */
    public DoWithin(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description, startDate);
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return ("[W]" + super.toString() + " within " + super.getStartDate() + " to " + this.endDate);
    }

    public String getWithin() {
        return super.getStartDate().toString() + " | " + this.endDate.toString();
    }
}
