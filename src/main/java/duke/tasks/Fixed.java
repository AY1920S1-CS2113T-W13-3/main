package duke.tasks;

public class Fixed extends Task {
    private int hour;
    private int min;
    /**
     * Initializes a task not yet done with the given description.
     *
     * @param description A description of this task.
     */
    public Fixed(String description , int hour , int min) {
        super(description);
        this.hour = hour;
        this.min = min;
    }

    @Override
    public String toString() {
        return "[F]" + super.toString() + "(needs " + (this.hour != 0 ? this.hour + " hours ":"") + (this.min != 0 ? this.min + " mins ":"") + ")";
    }
}
