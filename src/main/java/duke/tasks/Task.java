package duke.tasks;

import java.time.LocalDateTime;

/**
 * A generic task, which can be marked as done.
 */
public class Task {
    private String description;
    protected Boolean hasDate = false;
    protected LocalDateTime startDate = null;

    private boolean isDone;

    /**
     * Initializes a task not yet done with the given description.
     *
     * @param description A description of this task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description associated with this task.
     *
     * @return This task's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status of the task, true/false.
     *
     * @return True/false.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets this task as done or undone.
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Returns true if both tasks are the same.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getDescription().equals(getDescription());
    }

    /**
     * Returns a string representation of this task.
     *
     * @return The desired string representation.
     */
    @Override
    public String toString() {
        return (isDone ? "[✓] " : "[✘] ") + description;
    }

    public Boolean hasDate() {
        return hasDate;
    }

    public LocalDateTime getDate() {
        return startDate;
    }
}
