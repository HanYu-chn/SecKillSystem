package com.seckillSystem.service;

public interface CacheService {

    public void setValue(String key, Object value);

    public Object getValue(String key);

}
