package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when file is not saved.
 */
public class UnknownConstraintException extends SingaporeTravelException {

    /**
     * Constructs the Exception.
     */
    public UnknownConstraintException() {
        super(Messages.ERROR_CONSTRAINT_UNKNOWN);
    }
}
