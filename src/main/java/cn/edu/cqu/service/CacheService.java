package cn.edu.cqu.service;


//用于本地缓存的接口

public interface CacheService {
    //存方法
    void setCommonCache(String key, Object value);

    //取方法
    Object getFromCommonCache(String key);
}
