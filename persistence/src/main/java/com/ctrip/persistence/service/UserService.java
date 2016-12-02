package com.ctrip.persistence.service;

import com.ctrip.persistence.entity.MLFlow;
import com.ctrip.persistence.entity.User;
import com.ctrip.persistence.pojo.DataResult;
import com.ctrip.persistence.pojo.Result;

import java.util.List;


/**
 * 用户信息服务层
 *
 * @author 张峻滔
 */
public interface UserService {
    void test();

    void setUserInfo(List<MLFlow> flows);

    /**
     * 登陆
     */
    Result login(String username, String password);

    User findById(Long id);

    DataResult<List<User>> findAll();

    Result addUser(User user);

    Result deleteUser(Long id);

    Result resetPassword(Long id);

    User findByUsername(String username);
    
    String getUsernameById(Long id);
}
