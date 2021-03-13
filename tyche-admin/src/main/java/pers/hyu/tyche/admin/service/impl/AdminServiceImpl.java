package pers.hyu.tyche.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.hyu.tyche.admin.mapper.AdminsMapper;
import pers.hyu.tyche.admin.pojo.Admins;
import pers.hyu.tyche.admin.pojo.AdminsExample;
import pers.hyu.tyche.admin.service.AdminService;
import pers.hyu.tyche.utils.MD5Utils;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminsMapper adminsMapper;


    @Override
    public Admins selectAdminForLogin(String username, String password) throws Exception {
        System.out.println(MD5Utils.getMD5Str("admin"));

        AdminsExample example = new AdminsExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(MD5Utils.getMD5Str(password));
        List<Admins> adminsList = adminsMapper.selectByExample(example);
        return adminsList.size() > 0 ? adminsMapper.selectByExample(example).get(0) : null;
    }


}
