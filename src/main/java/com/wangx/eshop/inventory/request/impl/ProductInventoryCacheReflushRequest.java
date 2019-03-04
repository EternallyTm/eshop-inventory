package com.wangx.eshop.inventory.request.impl;

import com.wangx.eshop.inventory.model.ProductInventory;
import com.wangx.eshop.inventory.request.Request;
import com.wangx.eshop.inventory.service.ProductInventoryService;

/**
 *
 * 当读取缓存，缓存中没有数据时，先查询数据，在set到缓存中
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class ProductInventoryCacheReflushRequest implements Request {

    private ProductInventory productInventory;
    /**
     * 商品库存Service
     */
    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheReflushRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        //从数据库中查出最新的商品数量
        ProductInventory productInventoryCache = productInventoryService.findProductInventory(productInventory);
        //将数据库中最新的商品数量flush到redis缓存中
        productInventoryService.setProductInventoryCache(productInventoryCache);
    }

    /**
     * 获取商品ID
     * @return
     */
    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }
}
