package com.wangx.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wangx.eshop.inventory.dao.RedisDAO;
import com.wangx.eshop.inventory.mapper.UserMapper;
import com.wangx.eshop.inventory.model.User;
import com.wangx.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/1
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisDAO redisDAO;

    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public User findCacheUserInfo() {
        redisDAO.set("cached_user_lisi","{\"name\":\"lisi\",\"age\":28}");
        String userJSON = redisDAO.get("cached_user_lisi");
        JSONObject jsonObject = JSONObject.parseObject(userJSON);
        User user = new User();
        user.setName(jsonObject.getString("name"));
        user.setAge(jsonObject.getInteger("age"));
        return user;
    }
}
