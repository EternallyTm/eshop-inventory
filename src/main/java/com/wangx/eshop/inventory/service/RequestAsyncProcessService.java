package com.wangx.eshop.inventory.service;

import com.wangx.eshop.inventory.request.Request;

/**
 *
 * 请求异步处理Service
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public interface RequestAsyncProcessService {

    /**
     * 请求异步处理
     * @param request
     */
    void process(Request request);

}
