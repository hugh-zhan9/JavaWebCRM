package com.hugh.crm.dao;

import com.hugh.crm.pojo.Users;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public int insertUser(Users user);
    public Users login(Map<String, String> map);
    List<Users> selectAllUsers();
}
