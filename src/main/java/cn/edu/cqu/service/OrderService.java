package cn.edu.cqu.service;

import cn.edu.cqu.error.BusinessException;
import cn.edu.cqu.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount, String stockLogId) throws BusinessException;
}
