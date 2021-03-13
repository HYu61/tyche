package pers.hyu.tyche.admin.service;


import pers.hyu.tyche.admin.pojo.Admins;

public interface AdminService {

    Admins selectAdminForLogin(String username, String password) throws Exception;

}
