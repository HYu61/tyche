package pers.hyu.tyche.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.hyu.tyche.dao.VipVideoAccessMapper;
import pers.hyu.tyche.enums.UserStatus;
import pers.hyu.tyche.pojo.entity.VipVideoAccess;
import pers.hyu.tyche.pojo.entity.VipVideoAccessExample;
import pers.hyu.tyche.pojo.model.dto.UserDto;
import pers.hyu.tyche.service.UserService;
import pers.hyu.tyche.service.VipVideoAccessService;
import pers.hyu.tyche.utils.EntityUtils;

/**
 * @see pers.hyu.tyche.service.VipVideoAccessService
 *
 * @author Heng Yu
 * @version 1.0
 */
@Service
public class VipVideoAccessImpl implements VipVideoAccessService {
    @Autowired
    private UserService userService;

    @Autowired
    EntityUtils entityUtils;

    @Autowired
    private VipVideoAccessMapper vipVideoAccessMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addUserAccess(String publisherId, String userId, String accessAnswer) {
        UserDto publisher = userService.getUserById(publisherId);
        if(publisher == null){
            return 0;
        }

        // get vip video publisher's answer
        String publisherAnswer =publisher.getVipVideoAccessAnswer();

        // check if the login user's answer correct
        if(!accessAnswer.equals(publisherAnswer)){
            return -1;
        }

        // if correct add the relationship
        VipVideoAccess vipVideoAccess = new VipVideoAccess();
        vipVideoAccess.setId(entityUtils.generateId());
        vipVideoAccess.setPublisherId(publisherId);
        vipVideoAccess.setUserId(userId);

        return vipVideoAccessMapper.insert(vipVideoAccess);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isUserAccessible(String publisherId, String userId) {

        // Vip user can access all vip videos
        UserDto loginUser = userService.getUserById(userId);
        if(loginUser != null && loginUser.getStatus() == UserStatus.VIP.getStatusCode()){
            return true;
        }

        // The login user that answered the publisher's vip video access question can access the vip video
        VipVideoAccessExample example = new VipVideoAccessExample();
        example.createCriteria().andPublisherIdEqualTo(publisherId)
                .andUserIdEqualTo(userId);
        PageHelper.startPage(0,1);
        int result = vipVideoAccessMapper.selectByExample(example).size();
        return  result > 0;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int removeUserAccess(String publisherId) {
        VipVideoAccessExample example = new VipVideoAccessExample();
        example.createCriteria().andPublisherIdEqualTo(publisherId);

        return vipVideoAccessMapper.deleteByExample(example);
    }
}
