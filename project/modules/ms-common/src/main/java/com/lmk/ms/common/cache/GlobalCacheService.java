package com.lmk.ms.common.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 全局缓存服务
 * 只封装了常用操作，对于列表、哈希等操作可以自行通过RedisTemplate完成
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/13
 */
public class GlobalCacheService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    /**
     * 设置对象缓存，默认30分钟有效期
     * @param key       缓存KEY
     * @param value     缓存对象，需要实现Serializable接口
     */
    public void set(String key, Object value){
        set(key, value, 30, TimeUnit.MINUTES);
    }

    /**
     * 设置对象缓存，默认30分钟有效期
     * @param key       缓存KEY
     * @param mapKey    Map中的key
     * @param value     缓存对象，需要实现Serializable接口
     */
    public void mapSet(String key, String mapKey, Object value){
        redisTemplate.opsForHash().put(key, mapKey, value);
    }

    /**
     * 设置对象缓存
     * @param key       缓存KEY
     * @param value     缓存对象，需要实现Serializable接口
     * @param time      过期时间
     * @param unit      时间单位
     */
    public void set(String key, Object value, long time, TimeUnit unit){
        redisTemplate.opsForValue().set(key, value, time, unit);
    }

    /**
     * 判断是否存在缓存Key
     * @param key   缓存KEY
     * @return
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取缓存
     * @param key  缓存KEY
     * @return
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取KEY的过期时间，默认单位：秒
     * @param key
     * @return
     */
    public long getExpire(String key){
        if(hasKey(key)){
            return redisTemplate.opsForValue().getOperations().getExpire(key, TimeUnit.SECONDS);
        }
        return 0;
    }

    /**
     * 获取KEY的过期时间
     * @param key
     * @param unit
     * @return
     */
    public long getExpire(String key, TimeUnit unit){
        if(hasKey(key)){
            return redisTemplate.opsForValue().getOperations().getExpire(key, unit);
        }
        return 0;
    }

    /**
     * 获取缓存Map中的值
     * @param key       缓存KEY
     * @param mapKey    MAP中的KEY
     * @return
     */
    public Object mapGet(String key, String mapKey){
        return redisTemplate.opsForHash().get(key, mapKey);
    }

    /**
     * 刷新缓存
     * @param key       缓存KEY
     * @param time      过期时间
     * @param unit      时间单位
     */
    public void refresh(String key, long time, TimeUnit unit){
        redisTemplate.expire(key, time, unit);
    }

    /**
     * 删除缓存
     * @param key   缓存KEY
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }

    /**
     * 清空缓存，会清空当前数据库中所有的key，请谨慎使用
     */
    public void cleanAll(){
        Set<Object> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }


}