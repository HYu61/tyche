package pers.hyu.tyche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Example;
import io.swagger.models.auth.In;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.UserException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pers.hyu.tyche.dao.UsersFollowersMapper;
import pers.hyu.tyche.dao.UsersMapper;
import pers.hyu.tyche.dao.UsersMapperCustomized;
import pers.hyu.tyche.dao.VipVideoAccessMapper;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.enums.FolderNameEnum;
import pers.hyu.tyche.enums.RedisKeyEnum;
import pers.hyu.tyche.enums.UserStatus;
import pers.hyu.tyche.exception.UserServiceException;
import pers.hyu.tyche.pojo.entity.*;
import pers.hyu.tyche.pojo.model.dto.UserDto;
import pers.hyu.tyche.pojo.model.response.UserResponseModel;
import pers.hyu.tyche.service.UserService;
import pers.hyu.tyche.service.VipVideoAccessService;
import pers.hyu.tyche.utils.*;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Heng Yu
 * @version 1.0
 * @see pers.hyu.tyche.service.UserService
 * @see pers.hyu.tyche.service.UploadFileService
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${file-space}")
    private String fileSpace;

    @Value("${vip-pass}")
    private String VIP_PASS;

    @Autowired
    private EntityUtils entityUtils;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersMapperCustomized usersMapperCustomized;

    @Autowired
    private UsersFollowersMapper usersFollowersMapper;

    @Autowired
    private VipVideoAccessService vipVideoAccessService;


    @Transactional(readOnly = true)
    @Override
    public boolean isUserExists(String usernameOrId) {
        return usersMapperCustomized.selectExistsUser(usernameOrId) != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String registerUser(UserDto userDto) throws Exception {
        Users user = new Users();
        BeanUtils.copyProperties(userDto, user);
        user.setId(entityUtils.generateId());
        user.setNickname(userDto.getUsername()); // set the nickname as the username
        user.setPassword(MD5Utils.getMD5Str(userDto.getPlainPassword())); //crept the password

        // Using selective will auto set the filed to its default value like the status=1, fans_count=0 ect.
        usersMapper.insertSelective(user);
        return user.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean resetPassword(String username, String securityAnswer, String newPassword) throws Exception {
        Users user = usersMapperCustomized.selectByUsernameAndSecurityAnswer(username, securityAnswer);
        if (user == null) {
            return false;
        }

        user.setPassword(MD5Utils.getMD5Str(newPassword));
        usersMapper.updateByPrimaryKeySelective(user);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserForLogin(String username, String plainPassword) throws Exception {

        Users loginUser = usersMapperCustomized.selectByUsernameAndPassword(username, MD5Utils.getMD5Str(plainPassword));
        UserDto result = null;
        if(loginUser != null){
            result = new UserDto();
           BeanUtils.copyProperties(loginUser, result);
        }
        return result;
    }

    @Override
    public String setLoginUserToken(String userId) {
        String key = RedisKeyEnum.USER_SESSION_TOKEN_KEY.getKey() + "::" + userId;
        String userToken = UUID.randomUUID().toString();
        stringRedisTemplate.boundValueOps(key).set(userToken, RedisKeyEnum.USER_SESSION_TOKEN_KEY.getExpiredTimeInSecond(), TimeUnit.SECONDS);
        return userToken;
    }

    @Override
    public void removeLoginUserToken(String userId) {
        String key = RedisKeyEnum.USER_SESSION_TOKEN_KEY.getKey() + "::" + userId;
        stringRedisTemplate.delete(key);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        return userDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int editUser(String userId, UserDto userDto, boolean deleteVipVideoAccess) {

        // get the updated info
        Users updateUser = new Users();
        BeanUtils.copyProperties(userDto, updateUser);
        updateUser.setId(userId);

        // update the user
        int result =  usersMapper.updateByPrimaryKeySelective(updateUser);

        // delete the vip video access relationship if the user has edit the access question and answer
        if(deleteVipVideoAccess){
           vipVideoAccessService.removeUserAccess(userId);
        }

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addUserFollowRelation(String loginUserId, String publisherId) {
        UsersFollowers usersFollowers = new UsersFollowers();
        usersFollowers.setId(entityUtils.generateId());
        usersFollowers.setUserId(publisherId);
        usersFollowers.setFollowerId(loginUserId);
        usersFollowersMapper.insert(usersFollowers);

        //add the follow and the follower's count to each user
        usersMapperCustomized.addFollowerCount(publisherId);
        usersMapperCustomized.addFollowCount(loginUserId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removeUserFollowRelation(String loginUserId, String publisherId) {
        UsersFollowersExample example = new UsersFollowersExample();
        example.createCriteria().andUserIdEqualTo(publisherId).andFollowerIdEqualTo(loginUserId);

        usersFollowersMapper.deleteByExample(example);

        // reduce the follow and the follower's count to each user
        usersMapperCustomized.reduceFollowerCount(publisherId);
        usersMapperCustomized.reduceFollowCount(loginUserId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isFollowed(String loginUserId, String publisherId) {
        UsersFollowersExample example = new UsersFollowersExample();
        example.createCriteria().andUserIdEqualTo(publisherId)
                .andFollowerIdEqualTo(loginUserId);

        // select the record limit 1
        PageHelper.startPage(0,1);
        List<UsersFollowers> resultList = usersFollowersMapper.selectByExample(example);
        return resultList.size()>0;
    }

    @Override
    public boolean vipPassed(String vipPass) {
        return vipPass.equals(VIP_PASS);
    }

    @Override
    public String getUserUploadFolder(String userId, String folderName) {
//      String separator = FileSystems.getDefault().getSeparator();
        String separator = "/";
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        String folderType;
        switch (folderName) {
            case "profile":
                folderType = separator + FolderNameEnum.PROFILE.getFolderName();
                break;
            case "videos":
                folderType = separator + FolderNameEnum.VIDEOS.getFolderName();
                break;
            case "cover":
                folderType = separator + FolderNameEnum.VIDEOS.getFolderName() +
                        separator + FolderNameEnum.COVER.getFolderName();
                break;
            case "vip":
                folderType = separator + FolderNameEnum.VIDEOS.getFolderName() +
                        separator + FolderNameEnum.VIP.getFolderName();
                break;
            case "temp":
                folderType = separator + FolderNameEnum.VIDEOS.getFolderName() +
                        separator + FolderNameEnum.TEMP.getFolderName();
                break;
            default:
                folderType = "";
        }

        String relativeFolderPath = new StringBuilder()
                .append(separator).append(FolderNameEnum.BASE.getFolderName())
                .append(separator).append(userId).append(folderType)
                .toString();

        return relativeFolderPath;
    }

    @Transactional(readOnly = true)
    @Override
    public PagedResult getUserFollows(String userId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<UserResponseModel> followsList = usersMapperCustomized.selectUserFollows(userId);
        PageInfo<UserResponseModel> pageInfo = new PageInfo<>(followsList);

        PagedResult result = new PagedResult();
        result.setPage(page); // start page
        result.setRecords(pageInfo.getTotal()); // the num of total records
        result.setTotal(pageInfo.getPages()); // the num of total pages
        result.setRows(followsList); // the records in one page
        return result;
    }

    @Override
    public void initFolder(String userId, String[] folderNames) {
        if (folderNames != null && folderNames.length > 0) {
            for (String folderName : folderNames) {
                String relativeFolder = getUserUploadFolder(userId, folderName);

                File folder = new File(fileSpace + relativeFolder);

                if (!folder.exists()) {
                    folder.mkdirs();
                }
            }
        }
    }

    @Override
    public UploadResult uploadFile(MultipartFile file, String dest) throws IOException {

        if (file.isEmpty() || file == null) {
            throw new UserServiceException(ErrorMessages.UPLOAD_FILE_NULL.getErrorMessage());
        }

        // upload the file
        String destFolder = fileSpace + dest;
        UploadResult result = fileUploadUtil.uploadFile(file, destFolder, true, true);
        return result;
    }

    /**
     * Delete the folder.
     *
     * @param userId     In which user's folder.
     * @param folderName The name of the folder.
     */
    @Override
    public void deleteFolderOrFile(String userId, String folderName) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(folderName)) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        String tempRelFolder = getUserUploadFolder(userId, folderName);
        String tempAbsFolder = fileSpace + tempRelFolder;
        File tempFile = new File(tempAbsFolder);
        if (tempFile.isDirectory() && tempFile.listFiles().length != 0) {
            for (File f : tempFile.listFiles()
            ) {
                f.delete();
            }
        }
    }
}
