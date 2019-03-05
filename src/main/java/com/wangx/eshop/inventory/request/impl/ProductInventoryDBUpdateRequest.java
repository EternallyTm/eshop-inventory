package com.wangx.eshop.inventory.request.impl;

import com.wangx.eshop.inventory.model.ProductInventory;
import com.wangx.eshop.inventory.request.Request;
import com.wangx.eshop.inventory.service.ProductInventoryService;

/**
 *
 * 当发生了商品交易时，就会发送请求过来，要求更新库存，这个请求就是DataUpdateRequest， 数据更新请求
 * 先删除缓存，再更新数据库
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class ProductInventoryDBUpdateRequest implements Request {

    private ProductInventory productInventory;

    private ProductInventoryService productInventoryService;

    public ProductInventoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }
    @Override
    public void process() {
        //删除redis缓存
        productInventoryService.removeProductInventoryCache(productInventory);
        //更新库存数量
        productInventoryService.updateProductInventory(productInventory);
    }

    /**
     * 获取商品id
     * @return
     */
    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }

    @Override
    public Boolean isForceFlush() {
        return false;
    }
}
