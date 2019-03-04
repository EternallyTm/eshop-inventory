package com.wangx.eshop.inventory.service.impl;

import com.wangx.eshop.inventory.request.Request;
import com.wangx.eshop.inventory.request.RequestQueue;
import com.wangx.eshop.inventory.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
@Service("requestAsyncProcessServiceImpl")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    @Override
    public void process(Request request) {
        //做请求路由，根据每个产品的id,路由到对应的内存队列中去
        ArrayBlockingQueue<Request> arrayBlockingQueue = getRoutingQueue(request.getProductId());
        //将请求放到对应的队列中，完成路由操作
        try {
            arrayBlockingQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayBlockingQueue<Request> getRoutingQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        //先获取productID的hash值
        String key = String.valueOf(productId);
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        //对hash值取模，将hash值路由到指定的内存队列中,
        int index = (requestQueue.queueSize() - 1) & hash;
        return requestQueue.getQueue(index);
    }
}
