package pers.hyu.tyche.admin.service;

import pers.hyu.tyche.admin.pojo.Users;
import pers.hyu.tyche.utils.PagedResult;

public interface UserService {
    PagedResult selectUsers(Users user, Integer page, Integer limit);
}
