package pers.hyu.tyche.enums;

/**
 * The enum contains the type of media.
 * It is used for file convert and also used the extension of a file.
 *
 * @author Heng Yu
 * @version 1.0
 */
public enum MediaTypeEnum {
    VIDEO("mp4"),
    AUDIO("mp3"),
    COVER_IMAGE("jpg");

    private String format;

    MediaTypeEnum(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

}
