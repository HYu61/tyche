package pers.hyu.tyche.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.hyu.tyche.admin.mapper.UsersMapper;
import pers.hyu.tyche.admin.pojo.Users;
import pers.hyu.tyche.admin.pojo.UsersExample;
import pers.hyu.tyche.admin.service.UserService;
import pers.hyu.tyche.utils.PagedResult;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public PagedResult selectUsers(Users user, Integer page, Integer limit) {
        String username = user == null ? "": user.getUsername();
        String nickname = user == null ? "": user.getNickname();

        PageHelper.startPage(page, limit);

        UsersExample example = new UsersExample();
        UsersExample.Criteria userCriteria = example.createCriteria();
        if(StringUtils.isNotBlank(username)){
            userCriteria.andUsernameLike("%" + username + "%");
        }
        if(StringUtils.isNotBlank(nickname)){
            userCriteria.andNicknameLike("%" + nickname + "%");
        }

        List<Users> usersList = usersMapper.selectByExample(example);

        PageInfo<Users> pageInfo = new PageInfo<>(usersList);
        PagedResult result = new PagedResult();
        result.setTotal(pageInfo.getPages());
        result.setRows(usersList);
        result.setPage(page);
        result.setRecords(pageInfo.getTotal());

        return result;

    }
}
