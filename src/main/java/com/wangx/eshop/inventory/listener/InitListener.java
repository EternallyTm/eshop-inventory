package com.wangx.eshop.inventory.listener;

import com.wangx.eshop.inventory.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * 初始化监听器
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class InitListener implements ServletContextListener {

    /**
     * 初始化方法，在容器启动时启动该方法,初始化线程池和内存队列
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        RequestProcessorThreadPool.init();
        System.out.println("##########线程池和内存队列初始化完成############");
    }
}
