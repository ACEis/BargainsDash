package cn.edu.cqu.service;

import cn.edu.cqu.service.model.PromoModel;

public interface PromoService {

    PromoModel getPromoByItemId(Integer itemId);

    void publishPromo(Integer promoId);

    //生成秒杀用的令牌
    String generateSecondKillToken(Integer promoId, Integer itemId, Integer userId);
}
