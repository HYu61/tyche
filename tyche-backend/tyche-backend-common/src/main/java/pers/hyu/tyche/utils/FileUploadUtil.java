package pers.hyu.tyche.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * This is a util class that can upload the file and rename the file.
 *
 * @author Heng Yu
 * @version 1.0
 */
@Component
public class FileUploadUtil {

    /***
     * Upload the file to the destination path.
     *
     * @param file The original file needs to be upload.
     * @param dest The destination where the upload file stored.
     * @param overWrite If delete all the files in the dest folder.
     *                  True means delete all the files in the parent folder of the file will be stored.
     * @param formatName If it is true, then format the filename; otherwise use the original filename.
     * @return The UploadResult that contain the new name of the file and the filename without the extension.
     * @throws IOException
     */
    public UploadResult uploadFile(MultipartFile file, String dest, boolean overWrite, boolean formatName) throws IOException {
        String newFileName = formatName ? formatFileName(file.getOriginalFilename()) : file.getOriginalFilename();
//        String separator = FileSystems.getDefault().getSeparator();
        String separator = "/";
        String absPath = dest + separator + newFileName;

        File storedFile = new File(absPath);

        // create the directory
        if (!storedFile.getParentFile().isDirectory() || storedFile.getParentFile() == null) {
            storedFile.mkdirs();
        }

        // delete all the files in the folder
        if (overWrite) {
            for (File listFile : storedFile.getParentFile().listFiles()) {
                listFile.delete();
            }
        }

        // copy the file
        file.transferTo(storedFile);

        // store the file name into the result, and return it
        String fileNameWithoutExtension = newFileName.substring(0, newFileName.lastIndexOf("."));
        UploadResult result = new UploadResult(fileNameWithoutExtension, newFileName);

        return result;
    }

    /***
     * Rename the name of the upload file in the following pattern: the date plus a uuid, eg. 20201110dfkjds35lkerd.mp3.
     *
     * @param originalFilename The original name of the upload file.
     * @return The new name of the upload file.
     */
    private String formatFileName(String originalFilename) {
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        sb.append(UUID.randomUUID().toString().replace("-", ""));
        sb.append(originalFilename.substring(originalFilename.lastIndexOf(".")));
        return sb.toString();
    }

}
