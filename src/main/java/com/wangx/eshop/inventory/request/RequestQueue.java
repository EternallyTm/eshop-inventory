package com.wangx.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class RequestQueue {

    /**
     * 初始化请求队列
     */
    private List<ArrayBlockingQueue<Request>> blockingQueues = new ArrayList<>();

    /**
     * 标识位map
     */
    private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();

    /**
     * 使用内部类去初始化单例，保证绝对的线程安全
     */
    private static class Singleton{
        private static RequestQueue instance;

        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }
    }

    /**
     * jvm的机制去保证并发安全
     * 内部类的初始化，一定只会发生一次，不管多少个线程并发去初始化
     * @return
     */
    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 添加已给内存队列
     * @param queue
     */
    public void addQueue(ArrayBlockingQueue<Request> queue) {
        this.blockingQueues.add(queue);
    }

    /**
     * 获取内存队列的数量
     * @return
     */
    public int queueSize() {
        return this.blockingQueues.size();
    }

    /**
     * 获取index下标所对应的队列
     * @param index
     * @return
     */
    public ArrayBlockingQueue<Request> getQueue(int index) {
        return this.blockingQueues.get(index);
    }

    /**
     * 获取表示位Map
     * @return
     */
    public Map<Integer,Boolean> getFlagMap() {
        return flagMap;
    }
}
