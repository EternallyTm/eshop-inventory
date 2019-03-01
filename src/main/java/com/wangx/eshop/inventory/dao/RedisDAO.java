package com.wangx.eshop.inventory.dao;

/**
 * redis 缓存操作测试接口
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/1
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public interface RedisDAO {

    /**
     * set 操作
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 根据key得到value
     * @param key
     * @return
     */
    String get(String key);

}
