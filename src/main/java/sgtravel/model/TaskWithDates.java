package sgtravel.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents Tasks with a date field.
 */
public class TaskWithDates extends Task implements Serializable {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Constructs a TaskWithDates object.
     *
     * @param description The description of the task.
     * @param startDate The start date of the task.
     * @param endDate The end date of the task.
     */
    protected TaskWithDates(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getDescription().equals(getDescription())
                && otherTask instanceof TaskWithDates
                && ((TaskWithDates) otherTask).getStartDate().isEqual(getStartDate())
                && ((TaskWithDates) otherTask).getEndDate().isEqual(getEndDate());
    }
}

