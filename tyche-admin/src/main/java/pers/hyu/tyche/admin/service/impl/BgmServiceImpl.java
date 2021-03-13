package pers.hyu.tyche.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import pers.hyu.tyche.admin.mapper.BgmMapper;
import pers.hyu.tyche.admin.pojo.Bgm;
import pers.hyu.tyche.admin.pojo.BgmExample;
import pers.hyu.tyche.admin.service.BgmService;
import pers.hyu.tyche.admin.service.zkcurator.ZKCurator;
import pers.hyu.tyche.enums.BGMOperatorTypeEnum;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.utils.*;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see pers.hyu.tyche.admin.service.BgmService
 */
@Service
public class BgmServiceImpl implements BgmService {


    @Value("${fileSpace}")
    private String fileSpace;
    @Autowired
    private EntityUtils entityUtils;
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private ZKCurator zkCurator;

    @Override
    public void addBgm(Bgm bgm) {
        bgm.setId(entityUtils.generateId());
        bgmMapper.insert(bgm);  // insert the bgm into the db

        // send  add operation
        sentBgmOperation(bgm, BGMOperatorTypeEnum.ADD.getCode());
    }

    @Override
    public UploadResult uploadBgm(MultipartFile file, String dest) throws IOException {
//        String fileSpace = "C:" + File.separator + "tyche";

        if (file.isEmpty() || file == null) {
            throw new RuntimeException(ErrorMessages.UPLOAD_FILE_NULL.getErrorMessage());
        }

        // upload the file
        String destFolder = fileSpace + dest;
        UploadResult result = fileUploadUtil.uploadFile(file, destFolder, false, true);
        return result;
    }

    @Override
    public PagedResult selectAllBgm(Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);
        List<Bgm> bgmList = bgmMapper.selectByExample(new BgmExample());

        PageInfo<Bgm> pageInfo = new PageInfo<>(bgmList);

        PagedResult result = new PagedResult();
        result.setTotal(pageInfo.getPages());
        result.setRows(bgmList);
        result.setPage(page);
        result.setRecords(pageInfo.getTotal());
        return result;
    }

    @Override
    public void deleteBgmById(String id) {
        Bgm bgm = bgmMapper.selectByPrimaryKey(id);
        bgmMapper.deleteByPrimaryKey(id); // delete the bgm from db.

        // send  delete operation
        sentBgmOperation(bgm, BGMOperatorTypeEnum.DELETE.getCode());

        // Delete the bgm from the hard driver
        String bgmPath = bgm.getPath();
        File deleteFile = new File(fileSpace + bgmPath);
        if(deleteFile.isFile()){
            deleteFile.delete();
        }

    }

    /**
     *
     * @param bgm
     * @param code
     */
    private void sentBgmOperation(Bgm bgm, String code){
        Map<String, String> zkOperationMap = new HashMap<>();
        zkOperationMap.put("code", code);
        zkOperationMap.put("path", bgm.getPath());
        zkCurator.sendBgmOperation(bgm.getId(), zkOperationMap);
    }
}
