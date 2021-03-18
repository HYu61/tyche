package pers.hyu.tyche.enums;

/**
 * The enum contains the error messages for the different error.
 *
 * @author Heng Yu
 * @version 1.0
 */

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field."),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Record with provided is is not found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    UPLOAD_FILE_NULL("Upload file is null"),
    FORBIDDEN("Status is inactive"),
    ANSWER_INCORRECT("Vip Video Access Answer Incorrect");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
