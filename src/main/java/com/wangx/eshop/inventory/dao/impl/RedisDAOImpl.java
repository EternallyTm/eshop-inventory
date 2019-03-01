package com.wangx.eshop.inventory.dao.impl;

import com.wangx.eshop.inventory.dao.RedisDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

/**
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/1 0001
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */

@Repository
public class RedisDAOImpl implements RedisDAO {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key,value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }
}
