package pers.hyu.tyche.admin.service;


import org.springframework.web.multipart.MultipartFile;
import pers.hyu.tyche.admin.pojo.Bgm;
import pers.hyu.tyche.utils.PagedResult;
import pers.hyu.tyche.utils.UploadResult;

import java.io.IOException;

public interface BgmService {
    void addBgm(Bgm bgm);

    /**
     * Upload the bgm.
     *
     * @param file The bgm need to be upload.
     * @param path The relative path for the upload bgm need to be saved.
     * @return The UploadResult that contain the new name of the file and the filename without the extension.
     * @throws IOException
     */
    UploadResult uploadBgm(MultipartFile file, String path) throws IOException;

    PagedResult selectAllBgm(Integer page, Integer pageSize);

    void deleteBgmById(String id);
}
