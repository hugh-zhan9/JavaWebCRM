package com.hugh.crm.dao;

import com.hugh.crm.pojo.Users;

import java.util.List;
import java.util.Map;

public interface UserDao {
    int insertUser(Users user);
    Users login(Map<String, String> map);
    List<Users> selectAllUsers();
}
