package cn.edu.cqu.service;

import cn.edu.cqu.service.model.PromoModel;

public interface PromoService {

    PromoModel getPromoByItemId(Integer itemId);

    void publishPromo(Integer promoId);
}
