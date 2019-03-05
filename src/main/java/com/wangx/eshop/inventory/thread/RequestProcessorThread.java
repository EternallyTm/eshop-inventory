package com.wangx.eshop.inventory.thread;

import com.wangx.eshop.inventory.request.Request;
import com.wangx.eshop.inventory.request.RequestQueue;
import com.wangx.eshop.inventory.request.impl.ProductInventoryCacheReflushRequest;
import com.wangx.eshop.inventory.request.impl.ProductInventoryDBUpdateRequest;

import java.util.Map;
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
        try {
            while (true) {
                //ArrayBlockingQueue
                //Blocking表示，如果队列满了，或者是空的，那么就会在执行操作的时候，阻塞住
                Request request = blockingQueue.take();
                Boolean isForceFlush = request.isForceFlush();
                System.out.println("进入队列，开始运行队列中的request，ProductId：" + request.getProductId());

                //判断是否强制执行刷新缓存操作
                if (!isForceFlush) {
                    //先做都请求路由
                    RequestQueue requestQueue = RequestQueue.getInstance();
                    Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();
                    if (request instanceof ProductInventoryDBUpdateRequest) {
                        //如果是一个写请求，说明必然会跟新数据库，就将这个productId所对应的标识置为true
                        flagMap.put(request.getProductId(), true);
                    } else if (request instanceof ProductInventoryCacheReflushRequest) {
                        Boolean flag = flagMap.get(request.getProductId());
                        //如果是刷新缓存的请求，那么就判断，如果标识不为空，而且是true,那么说明再这个之前有一个这个商品的数量被更新
                        if (flag != null && flag) {
                            flagMap.put(request.getProductId(),false);
                        }
                        //如果是缓存刷新的请求，而且发现表示不为空，但是标识为false，这就说明前面已经有一个数据跟新请求，一个缓存刷新请求了，这就不用再做任何操作了，
                        //就不应该将该请求加入到队列中进行等待了
                        if (flag != null && !flag) {
                            return true;
                        }

                        //如果flag是null，
                        if (flag == null) {
                            flagMap.put(request.getProductId(), false);
                        }
                    }
                }
                //执行process
                request.process();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
