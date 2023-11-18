package com.ican.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis服务接口实现类
 *
 * @author ican
 */
@Service
@SuppressWarnings("all")
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public Boolean setExpire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }


    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }


    public Collection<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }


    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    public <T> void setObject(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }


    public <T> void setObject(String key, T value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }


    public <T> T getObject(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }


    public Boolean deleteObject(String key) {
        return redisTemplate.delete(key);
    }


    public Long deleteObject(List<String> keys) {
        return redisTemplate.delete(keys);
    }


    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }


    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, -delta);
    }


    public <T> void setHash(String key, String hashKey, T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }


    public <T> Boolean setHash(String key, String hashKey, T value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return setExpire(key, timeout, timeUnit);
    }


    public <T> void setHashAll(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }


    public <T> Boolean setHashAll(String key, Map<String, T> map, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, map);
        return setExpire(key, timeout, timeUnit);
    }


    public <T> T getHash(String key, String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }


    public Map getHashAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    public <T> void deleteHash(String key, T... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }


    public Boolean hasHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }


    public Long incrHash(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }


    public Long decrHash(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }


    public <T> Long setList(String key, T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }


    public <T> Long setList(String key, T value, long timeout, TimeUnit timeUnit) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        setExpire(key, timeout, timeUnit);
        return count;
    }


    public <T> Long setListAll(String key, T... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }


    public <T> Long setListAll(String key, long timeout, TimeUnit timeUnit, T... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        setExpire(key, timeout, timeUnit);
        return count;
    }


    public <T> List<T> getList(String key, long start, long end) {
        List<T> result = (List<T>) redisTemplate.opsForList().range(key, start, end);
        return result;
    }


    public <T> T getListByIndex(String key, long index) {
        return (T) redisTemplate.opsForList().index(key, index);
    }


    public Long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }


    public <T> Long deleteList(String key, long count, T value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }


    public <T> Long setSet(String key, T... values) {
        return redisTemplate.opsForSet().add(key, values);
    }


    public <T> Long setSet(String key, long timeout, TimeUnit timeUnit, T... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        setExpire(key, timeout, timeUnit);
        return count;
    }


    public <T> Set<T> getSet(String key) {
        Set<T> result = (Set<T>) redisTemplate.opsForSet().members(key);
        return result;
    }


    public <T> Long deleteSet(String key, T... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }


    public <T> Boolean hasSetValue(String key, T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }


    public Long getSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }


    public <T> Double incrZet(String key, T value, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }


    public <T> Double decrZet(String key, T value, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, -score);
    }


    public <T> Long deleteZetScore(String key, T... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }


    public Map<Object, Double> zReverseRangeWithScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end)
                .stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }


    public <T> Double getZsetScore(String key, T value) {
        return redisTemplate.opsForZSet().score(key, value);
    }


    public Map<Object, Double> getZsetAllScore(String key) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().rangeWithScores(key, 0, -1))
                .stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }


}
