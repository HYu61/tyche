package pers.hyu.tyche.utils;

/**
 * The class is used for contain the result of the upload file.
 * It stores the new name of the file and the name without the file extension.
 *
 * @author Heng Yu
 * @version 1.0
 */
public class UploadResult {
    private String fileNameWithoutExtension;
    private String fileName;

    public UploadResult(String fileNameWithoutExtension, String fileName) {
        this.fileNameWithoutExtension = fileNameWithoutExtension;
        this.fileName = fileName;
    }

    public String getFileNameWithoutExtension() {
        return fileNameWithoutExtension;
    }

    public String getFileName() {
        return fileName;
    }
}
