package pers.hyu.tyche.exception;

/**
 * This class is for handling the exception in the user service.
 *
 * @author Heng Yu
 * @version 1.0
 */
public class UserServiceException extends RuntimeException {
    private static final long serialVersionUID = -4220814337903178674L;

    public UserServiceException(String message) {
        super(message);
    }
}
