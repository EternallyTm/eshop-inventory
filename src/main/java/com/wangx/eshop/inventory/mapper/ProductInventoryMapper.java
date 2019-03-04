package com.wangx.eshop.inventory.mapper;

import com.wangx.eshop.inventory.model.ProductInventory;

/**
 * 库存数量持久层mapper
 * @author: wangx
 * @email: 1028106567@QQ.COM
 * @since: 2019/3/4 0004
 * @version: 1.0
 * Copyright: Copyright (c) 2019
 */
public interface ProductInventoryMapper {

   /**
    *
    * 更新商品库存
    * @param productInventory
    */
   void updateProductInventory(ProductInventory productInventory);

   /**
    * 根据商品id得到库存信息
    * @param productInventory
    * @return
    */
   ProductInventory findProductInventory(ProductInventory productInventory);
}
