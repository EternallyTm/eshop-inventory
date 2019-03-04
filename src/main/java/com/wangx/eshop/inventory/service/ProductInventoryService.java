package com.wangx.eshop.inventory.service;

import com.wangx.eshop.inventory.model.ProductInventory;

/**
 *
 * 商品库存Service接口
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public interface ProductInventoryService {

    /**
     *
     * 更新商品库存
     * @param productInventory 库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 删除redis缓存
     * @param productInventory 库存
     */
    void removeProductInventoryCache(ProductInventory productInventory);

    /**
     * 根据商品id得到库存信息
     * @param productInventory
     * @return
     */
    ProductInventory findProductInventory(ProductInventory productInventory);

    /**
     * 将商品信息set到缓存中
     * @param productInventory
     */
    void setProductInventoryCache(ProductInventory productInventory);

    /**
     * 从缓存中获取库存
     * @param productId
     * @return
     */
    ProductInventory getProductInventoryCache(Integer productId);
}
