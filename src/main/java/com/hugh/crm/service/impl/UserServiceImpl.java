package com.hugh.crm.service.impl;

import com.hugh.crm.dao.UserDao;
import com.hugh.crm.pojo.Users;
import com.hugh.crm.service.UserService;
import com.hugh.crm.util.DateTimeUtil;
import com.hugh.crm.util.MybatisUtil;
import com.hugh.crm.exception.LoginException;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    private UserDao userDao = MybatisUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public Users login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> userMap = new HashMap<>();
        userMap.put("loginAct",loginAct);
        userMap.put("loginPwd",loginPwd);

        Users user = userDao.login(userMap);

        if(user == null){
            throw new LoginException("账号密码错误");
        }
        // 如果程序执行到这，说明用户存在，继续向下验证

        // 验证失效时间
        String currentTime = DateTimeUtil.getSysTime();
        String expireTime = user.getExpireTime();
        if (expireTime.compareTo(currentTime) < 0){
            throw new LoginException("账号已失效");
        }

        // 验证锁定状态
        String lockState = user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("账号已锁定，请联系管理员");
        }

        // 验证ip地址
        String allowIps = user.getAllowIps();
        if (! allowIps.contains(ip)){
            throw new LoginException("ip地址受限，请联系管理员");
        }

        return user;
    }

    @Override
    public List<Users> selectAllUsers() {
        List<Users> users = userDao.selectAllUsers();
        return users;
    }


}
