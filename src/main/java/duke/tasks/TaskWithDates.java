package duke.tasks;

import java.time.LocalDateTime;

/**
 * Class for Tasks with a date field.
 */
class TaskWithDates extends Task {
    private LocalDateTime startDate;

    TaskWithDates(String description, LocalDateTime startDate) {
        super(description);
        this.startDate = startDate;
    }

    /**
     * Constructor to work around Tasks that should contain dates but does not.
     */
    TaskWithDates(String... description) {
        super(description[0]);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
}