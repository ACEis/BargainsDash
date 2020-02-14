package cn.edu.cqu.service;

import cn.edu.cqu.error.BusinessException;
import cn.edu.cqu.service.model.ItemModel;

import java.util.List;

public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItem();


    //商品详情浏览
    ItemModel getItemById(Integer id);

    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    //商品销量增加
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;
}
