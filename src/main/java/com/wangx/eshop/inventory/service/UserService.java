package com.wangx.eshop.inventory.service;

import com.wangx.eshop.inventory.model.User;

/**
 * 测试UserService
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/1
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public interface UserService {

    /**
     * 查询数据库User
     * @return
     */
    User findUserInfo();

    /**
     * 查询缓存User数据
     * @return
     */
    User findCacheUserInfo();
}
