package com.hugh.crm.service;

import com.hugh.crm.exception.LoginException;
import com.hugh.crm.pojo.Users;

import java.util.List;

public interface UserService {

    Users login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<Users> selectAllUsers();
}
