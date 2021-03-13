package pers.hyu.tyche.service;

import org.springframework.web.multipart.MultipartFile;
import pers.hyu.tyche.utils.UploadResult;

import java.io.IOException;

/**
 * The interface for the upload file service.
 * Including create the folders, upload the file, and delete the files in the folder.
 *
 * @author Heng Yu
 * @version 1.0
 */
public interface UploadFileService {
    /**
     * Create the folder for save the file.
     *
     * @param userId      Use the userId to create different folder.
     * @param folderNames The String array contains the names of the folders that to be created.
     * @return The relative path of the folder.
     */
    void initFolder(String userId, String[] folderNames);


    /**
     * Upload the file.
     *
     * @param file The file need to be upload.
     * @param dest The destination the file need to saved.
     * @return The UploadResult that contain the new name of the file and the filename without the extension.
     * @throws IOException
     */
    UploadResult uploadFile(MultipartFile file, String dest) throws IOException;


    /**
     * Delete the files in the folder.
     *
     * @param userId     In which user's folder.
     * @param folderOrFileName Specify the folder or the name under the user's folder.
     */
    void deleteFolderOrFile(String userId, String folderOrFileName);

}
