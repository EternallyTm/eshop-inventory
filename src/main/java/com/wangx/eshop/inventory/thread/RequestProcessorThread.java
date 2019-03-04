package com.wangx.eshop.inventory.thread;

import com.wangx.eshop.inventory.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class RequestProcessorThread implements Callable<Boolean> {

    private ArrayBlockingQueue<Request> blockingQueue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }


    @Override
    public Boolean call() throws Exception {
        while (true) {
            break;
        }
        return true;
    }
}
