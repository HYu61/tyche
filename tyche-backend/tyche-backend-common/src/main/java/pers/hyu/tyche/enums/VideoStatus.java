package pers.hyu.tyche.enums;

/**
 * The enum contains the status of the video.
 * -1 means the video is inactive.
 * 0 means the video is the regular video.
 * 1 means the video is the vip video.
 *
 * @author Heng Yu
 * @version 1.0
 */
public enum VideoStatus {
    INACTIVE_VIDEO(-1),
    REGULAR_VIDEO(0),
    VIP_VIDEO(1);

    private int videoStatus;

    VideoStatus(int videoStatus) {
        this.videoStatus = videoStatus;
    }

    public int getVideoStatus() {
        return videoStatus;
    }
}
