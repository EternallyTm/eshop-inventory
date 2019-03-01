package com.wangx.eshop.inventory.contorller;

import com.wangx.eshop.inventory.model.User;
import com.wangx.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 测试用户类控制器
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/1
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findUserInfo")
    public User findUserInfo() {
        return userService.findUserInfo();
    }

    @RequestMapping("/findCacheUserInfo")
    public User findCacheUserInfo() {
        return userService.findCacheUserInfo();
    }
}
