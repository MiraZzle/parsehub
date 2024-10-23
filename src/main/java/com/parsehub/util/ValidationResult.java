package com.parsehub.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to represent the result of a validation operation.
 */
public class ValidationResult {
    
    private boolean valid;
    private List<String> errorMessages = new ArrayList<>();

    /**
     * Returns the validation status.
     *
     * @return true if the validation was successful; false otherwise.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Sets the validation status.
     *
     * @param valid the validation status to be set. true indicates a successful validation.
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Returns the list of error messages associated with the validation result.
     *
     * @return a list of error messages.
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Adds a new error message to the list of error messages.
     *
     * @param errorMessage the error message to add to the list.
     */
    public void addErrorMessage(String errorMessage) {
        this.errorMessages.add(errorMessage);
    }
}
