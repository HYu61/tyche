package pers.hyu.tyche.enums;

/**
 * The enum contains the names of folder.
 * This names is used for creating initial folder that store the files for the user.
 *
 * @author Heng Yu
 * @version 1.0
 */
public enum FolderNameEnum {
    BASE("users"),
    PROFILE("profile"),
    VIDEOS("videos"),
    COVER("cover"),
    VIP("vip"),
    TEMP("temp");

    private String folderName;

    FolderNameEnum(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

}
