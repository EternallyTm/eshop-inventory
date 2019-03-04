package com.wangx.eshop.inventory.service.impl;

import com.wangx.eshop.inventory.dao.RedisDAO;
import com.wangx.eshop.inventory.mapper.ProductInventoryMapper;
import com.wangx.eshop.inventory.model.ProductInventory;
import com.wangx.eshop.inventory.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品库存Service实现类
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */

@Service("productInventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private final static String KEYPREFIX = "product:inventory:";
    /**
     * 商品库存mapper
     */
    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    /**
     * redis操作的dao层
     */
    @Autowired
    private RedisDAO redisDAO;

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
    }

    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        //拼接缓存商品的key
        String key = KEYPREFIX + productInventory.getProductId();
        redisDAO.delete(key);

    }

    @Override
    public ProductInventory findProductInventory(ProductInventory productInventory) {
        return productInventoryMapper.findProductInventory(productInventory);
    }

    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        String key = KEYPREFIX + productInventory.getProductId();
        redisDAO.set(key,String.valueOf(productInventory.getInventoryCnt()));
    }

    @Override
    public ProductInventory getProductInventoryCache(Integer productId) {
        Long cnt = 0L;
        String key = KEYPREFIX + productId;
        String result = redisDAO.get(key);
        if (result != null && !"".equals(result)){
            try {
            cnt = Long.valueOf(result);
            ProductInventory productInventory = new ProductInventory();
            productInventory.setInventoryCnt(cnt);
            productInventory.setProductId(productId);
            return productInventory;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
