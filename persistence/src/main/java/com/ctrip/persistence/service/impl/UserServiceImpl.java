package com.ctrip.persistence.service.impl;

import com.ctrip.persistence.entity.MLFlow;
import com.ctrip.persistence.entity.User;
import com.ctrip.persistence.repository.UserRepository;
import com.ctrip.persistence.pojo.DataResult;
import com.ctrip.persistence.pojo.Result;
import com.ctrip.persistence.service.UserService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserRepository userRepository;

    @Transactional
    public void test() {
        User user = new User();
        user.setPassword("test1");
        user.setUsername("test1");
        user.setId(1l);
        user.setLastModified(new Date());
        userRepository.save(user);
        if (true) {
            throw new RuntimeException();
        }
    }

    public void setUserInfo(List<MLFlow> flows) {
        Set<Long> ownerIds = Sets.newHashSet();
        for(MLFlow flow:flows){
            ownerIds.add(flow.getOwnerId());
        }
        Map<Long, User> map = Maps.newHashMap();
        for (User user : userRepository.findAll(ownerIds)) {
            map.put(user.getId(), user);
        }
        for (MLFlow f : flows) {
            f.setOwner(map.get(f.getOwnerId()));
        }
    }

    public Result login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return new DataResult<>(user).setSuccess(true);
        }
        return new Result(false).setMsg("密码用户不匹配！");
    }

    public User findById(Long id) {
        return null;
    }

    public DataResult<List<User>> findAll() {
        return new DataResult<>(true, userRepository.findAll());
    }

    public Result addUser(User user) {
        userRepository.save(user);
        return new Result(true);
    }

    public Result deleteUser(Long id) {
        return null;
    }

    public Result resetPassword(Long id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	@Override
	public String getUsernameById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.getUsernameById(id);
	}
}
