/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.ui.response;

/**
 *
 * @author carlosndiaye
 */
public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required fields. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Record with provided id is not found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete this record");

    private String errorMessages;

    /**
     * @return the errorMessages
     */
    public String getErrorMessages() {
        return errorMessages;
    }

    /**
     * @param errorMessages the errorMessages to set
     */
    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    ErrorMessages(String errorMessage) {
        this.errorMessages = errorMessage;
    }
}
