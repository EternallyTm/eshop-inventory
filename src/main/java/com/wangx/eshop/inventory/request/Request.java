package com.wangx.eshop.inventory.request;

/**
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public interface Request {

    /**
     * 执行缓存aside cache pattern 操作
     */
    void process();

    /**
     * 获取商品ID
     * @return
     */
    Integer getProductId();
}
