package cn.edu.cqu.service;

import cn.edu.cqu.dataobject.StockLogDO;
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

    //item及promomodel缓存模型
    ItemModel getItemByIdInCache(Integer id);

    //Redis库存扣减
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    //Redis库存回补
    boolean increaseStock(Integer itemId, Integer amount);

    //发送库存扣减的异步消息
    boolean asyncDecreaseStock(Integer itemId, Integer amount);

    //商品销量增加
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;

    //初始化库存流水
    String initStockLog(Integer itemId, Integer amount);
}
