package pers.hyu.tyche.enums;

/**
 * The enum contains the different status of the user
 * -1 means the user is inactive.
 * 0 means the user is the regular user; the user can upload the video less than 30s and can only watch regular videos.
 * 1 means the user is the vip user; the vip user can upload 60s length video and can watch all the videos.
 *
 * @author Heng Yu
 * @version 1.0
 */
public enum UserStatus {
    INACTIVE(-1),
    ACTIVE(0),
    VIP(1);

    private int statusCode;

    UserStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
