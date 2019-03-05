package com.wangx.eshop.inventory.contorller;

import com.wangx.eshop.inventory.model.ProductInventory;
import com.wangx.eshop.inventory.request.Request;
import com.wangx.eshop.inventory.request.impl.ProductInventoryCacheReflushRequest;
import com.wangx.eshop.inventory.request.impl.ProductInventoryDBUpdateRequest;
import com.wangx.eshop.inventory.service.ProductInventoryService;
import com.wangx.eshop.inventory.service.RequestAsyncProcessService;
import com.wangx.eshop.inventory.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品库存控制器
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */

@RestController
@RequestMapping("/productInventory")
public class ProductInventoryController {

    /**
     * 商品库存Service
     */
    @Autowired
    private ProductInventoryService productInventoryService;

    /**
     * 异步处理请求的Service
     */
    @Autowired
    private RequestAsyncProcessService requestAsyncProcessService;

    /**
     * 更新商品库存
     * @param productInventory
     * @return
     */
    @RequestMapping("/updateProductInventory")
    public Response<ProductInventory> updateProductInventory(ProductInventory productInventory) {
        Response<ProductInventory> response = new Response<>();
        try {
            Request request = new ProductInventoryDBUpdateRequest(productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
            response.setStatus(Response.SUCCESS);
        }catch (Exception e) {
            response.setStatus(Response.FAILURE);
        }
        return response;
    }

    /**
     * 更新商品库存
     * @param productInventory
     * @return
     */
    @RequestMapping("/getProductInventory")
    public Response<ProductInventory> getProductInventory(ProductInventory productInventory) {
        Response<ProductInventory> response = new Response<>();
        try {
            System.out.println("接受到一个查询商品的读请求");
            Request request = new ProductInventoryCacheReflushRequest(productInventory, productInventoryService, false);
            requestAsyncProcessService.process(request);
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            //将请求扔给Service异步去处理后，就需要while(true)一会儿，在这里hang住，
            //去尝试等待前面有商品库存更新的操作，同时刷新缓存的操作，将最新的数据刷新到缓存中
            while (true) {
                //等到超过一段时间之后，说明没有数据，就直接退出循环
                if (waitTime > 200) {
                    break;
                }

                //尝试去redis中读取一次商品库存的缓存数据
                ProductInventory productInventoryCache = productInventoryService.getProductInventoryCache(productInventory.getProductId());
                //如果读到数据，就直接返回，
                if (productInventoryCache != null) {
                    response.setStatus(Response.SUCCESS);
                    response.setData(productInventoryCache);
                    return response;
                }
                //如果没有读到数据，就等待一段时间
                Thread.sleep(20);
                endTime = System.currentTimeMillis();
                waitTime = endTime - startTime;
            }
            //超过了200ms没有读到缓存中的数据，则尝试直接从数据库中读取数据
            ProductInventory productInventoryToDb = productInventoryService.findProductInventory(productInventory);
            if (productInventoryToDb != null) {

                //将从数据库中获取的数据刷新到缓存中,这里由于高并发的场景，所以直接修改刷新请求可能会出现并发问题，所以需要跳过都请求去重，强制刷新缓存
                Request forceFlushRequest = new ProductInventoryCacheReflushRequest(productInventory, productInventoryService, true);
                /**
                 * 代码运行到这里，会出现三种情况：
                 * 1. 上次也是都请求，数据刷新到了redis,但是数据被redis的LUR算法给清理掉了，标志位还是false，
                 * 所以这里请求是拿不到数据的，需要在放一个读request请求去刷新一下缓存
                 * 2.可能200ms内，就是都请求一直在队列中积压着，没有等待到它执行（加机器）
                 * 所以直接查一次数据库，然后给队列中塞进去一个刷新缓存的请求
                 * 3. 数据库里本身没有，缓存穿透，穿透redis,请求达到mysql库
                 */
                requestAsyncProcessService.process(forceFlushRequest);
                response.setStatus(Response.SUCCESS);
                response.setData(productInventoryToDb);
                return response;
            }
            response.setStatus(Response.SUCCESS);
        }catch (Exception e) {
            response.setStatus(Response.FAILURE);
        }
        //缓存和数据库中都拿不到数据，说明该商品没有库存
        ProductInventory productNoInventory = new ProductInventory();
        productNoInventory.setProductId(productInventory.getProductId());
        productNoInventory.setInventoryCnt(-1L);
        response.setStatus(Response.SUCCESS);
        response.setData(productNoInventory);
        return response;
    }
}
