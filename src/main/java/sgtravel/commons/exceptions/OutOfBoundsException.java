package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when index query is out of bounds.
 */
public class OutOfBoundsException extends SingaporeTravelException {

    /**
     * Constructs the Exception.
     */
    public OutOfBoundsException() {
        super(Messages.ERROR_INDEX_OUT_OF_BOUNDS);
    }
}
