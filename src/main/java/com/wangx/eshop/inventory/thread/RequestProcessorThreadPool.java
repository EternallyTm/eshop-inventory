package com.wangx.eshop.inventory.thread;


import com.wangx.eshop.inventory.request.Request;
import com.wangx.eshop.inventory.request.RequestQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 封装一个线程池：单例
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class RequestProcessorThreadPool {

    /**
     * 初始化十个线程的线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public RequestProcessorThreadPool () {
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0; i < 10; i++) {
            ArrayBlockingQueue<Request> blockingQueue = new ArrayBlockingQueue<>(100);
            requestQueue.addQueue(blockingQueue);
            threadPool.submit(new RequestProcessorThread(blockingQueue));
        }
    }

    /**
     * 使用内部类去初始化单例，保证绝对的线程安全
     */
    private static class Singleton{
        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }
    }

    /**
     * jvm的机制去保证并发安全
     * 内部类的初始化，一定只会发生一次，不管多少个线程并发去初始化
     * @return
     */
    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化方法
     */
    public static void init() {
        getInstance();
    }
}
