package com.ican.service.impl;

import com.ican.service.RedisService;
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
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Boolean setExpire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public Collection<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public <T> void setObject(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <T> void setObject(String key, T value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public <T> T getObject(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean deleteObject(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long deleteObject(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, -delta);
    }

    @Override
    public <T> void setHash(String key, String hashKey, T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public <T> Boolean setHash(String key, String hashKey, T value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return setExpire(key, timeout, timeUnit);
    }

    @Override
    public <T> void setHashAll(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public <T> Boolean setHashAll(String key, Map<String, T> map, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, map);
        return setExpire(key, timeout, timeUnit);
    }

    @Override
    public <T> T getHash(String key, String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Map getHashAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public <T> void deleteHash(String key, T... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    @Override
    public Boolean hasHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public Long incrHash(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @Override
    public Long decrHash(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    @Override
    public <T> Long setList(String key, T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public <T> Long setList(String key, T value, long timeout, TimeUnit timeUnit) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        setExpire(key, timeout, timeUnit);
        return count;
    }

    @Override
    public <T> Long setListAll(String key, T... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public <T> Long setListAll(String key, long timeout, TimeUnit timeUnit, T... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        setExpire(key, timeout, timeUnit);
        return count;
    }

    @Override
    public <T> List<T> getList(String key, long start, long end) {
        List<T> result = (List<T>) redisTemplate.opsForList().range(key, start, end);
        return result;
    }

    @Override
    public <T> T getListByIndex(String key, long index) {
        return (T) redisTemplate.opsForList().index(key, index);
    }

    @Override
    public Long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public <T> Long deleteList(String key, long count, T value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    @Override
    public <T> Long setSet(String key, T... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public <T> Long setSet(String key, long timeout, TimeUnit timeUnit, T... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        setExpire(key, timeout, timeUnit);
        return count;
    }

    @Override
    public <T> Set<T> getSet(String key) {
        Set<T> result = (Set<T>) redisTemplate.opsForSet().members(key);
        return result;
    }

    @Override
    public <T> Long deleteSet(String key, T... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    @Override
    public <T> Boolean hasSetValue(String key, T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Long getSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public <T> Double incrZet(String key, T value, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    @Override
    public <T> Double decrZet(String key, T value, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, -score);
    }

    @Override
    public <T> Long deleteZetScore(String key, T... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    @Override
    public Map<Object, Double> zReverseRangeWithScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end)
                .stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }

    @Override
    public <T> Double getZsetScore(String key, T value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    @Override
    public Map<Object, Double> getZsetAllScore(String key) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().rangeWithScores(key, 0, -1))
                .stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }


}
