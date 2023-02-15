package com.ican.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务接口
 *
 * @author ican
 */
@SuppressWarnings("all")
public interface RedisService {

    /**
     * 设置有效时间
     *
     * @param key      键
     * @param timeout  有效时间
     * @param timeUnit 时间单位
     * @return 是否成功
     */
    Boolean setExpire(String key, long timeout, TimeUnit timeUnit);

    /**
     * 获取有效时间
     *
     * @param key 键
     * @return 有效时间
     */
    Long getExpire(String key, TimeUnit timeUnit);

    /**
     * 获取所有的缓存对象
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    Collection<String> getKeys(String pattern);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    Boolean hasKey(String key);

    /**
     * 缓存基本的对象
     *
     * @param key   键
     * @param value 值
     */
    <T> void setObject(String key, T value);

    /**
     * 缓存基本的对象
     *
     * @param key      键
     * @param value    值
     * @param timeout  有效时间
     * @param timeUnit 时间单位
     */
    <T> void setObject(String key, T value, long timeout, TimeUnit timeUnit);

    /**
     * 获取缓存对象
     *
     * @param key 键
     * @return 缓存对象
     */
    <T> T getObject(String key);

    /**
     * 删除单个缓存对象
     *
     * @param key 键
     * @return 是否成功
     */
    Boolean deleteObject(String key);

    /**
     * 删除多个缓存对象
     *
     * @param keys 键
     * @return 删除数量
     */
    Long deleteObject(List<String> keys);

    /**
     * 按delta递增
     *
     * @param key   键
     * @param delta 递增因子（大于0）
     * @return 递增后的长度
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     *
     * @param key   键
     * @param delta 递减因子
     * @return 递减后的长度
     */
    Long decr(String key, long delta);

    /**
     * 向Hash结构中添加缓存
     *
     * @param key     外部键
     * @param hashKey 内部键
     * @param value   值
     */
    <T> void setHash(String key, String hashKey, T value);

    /**
     * 向Hash结构中添加缓存
     *
     * @param key     外部键
     * @param hashKey 内部键
     * @param value   值
     * @param timeout 有效时间
     * @return 是否成功
     */
    <T> Boolean setHash(String key, String hashKey, T value, long timeout, TimeUnit timeUnit);

    /**
     * 向Hash结构中添加Map
     *
     * @param key 键
     * @param map HashMap值
     */
    <T> void setHashAll(String key, Map<String, T> map);

    /**
     * 向Hash结构中添加Map
     *
     * @param key      键
     * @param map      HashMap值
     * @param timeout  有效时间
     * @param timeUnit 时间单位
     * @return 是否成功
     */
    <T> Boolean setHashAll(String key, Map<String, T> map, long timeout, TimeUnit timeUnit);

    /**
     * 获取Hash结构中的值
     *
     * @param key     外部键
     * @param hashKey 内部键
     * @return 值
     */
    <T> T getHash(String key, String hashKey);

    /**
     * 获取Hash结构
     *
     * @param key 键
     * @return Map集合
     */
    <T> Map<String, T> getHashAll(String key);

    /**
     * 删除Hash结构中的值
     *
     * @param key      外键键
     * @param hashKeys 内部键
     */
    <T> void deleteHash(String key, T... hashKeys);

    /**
     * 判断Hash结构中是否有该项的值
     *
     * @param key     外部键
     * @param hashKey 内部键
     * @return 是否存在
     */
    Boolean hasHashValue(String key, String hashKey);

    /**
     * Hash递增 如果不存在则创建一个值并把递增后的值返回
     *
     * @param key     外部键
     * @param hashKey 内部键
     * @param delta   递增因子
     * @return 递增后的值
     */
    Long incrHash(String key, String hashKey, Long delta);

    /**
     * Hash递减 如果不存在则创建一个值并把递减后的值返回
     *
     * @param key     外部键
     * @param hashKey 内部键
     * @param delta   递减因子
     * @return 递减后的值
     */
    Long decrHash(String key, String hashKey, Long delta);

    /**
     * 向List结构中添加缓存
     *
     * @param key   键
     * @param value 值
     * @return 缓存后的长度
     */
    <T> Long setList(String key, T value);

    /**
     * 向List结构中添加缓存
     *
     * @param key      键
     * @param value    值
     * @param timeout  有效时间
     * @param timeUnit 时间单位
     * @return 缓存后的长度
     */
    <T> Long setList(String key, T value, long timeout, TimeUnit timeUnit);

    /**
     * 向List结构中批量添加缓存
     *
     * @param key    键
     * @param values 值
     * @return 缓存后的长度
     */
    <T> Long setListAll(String key, T... values);

    /**
     * 向List结构中批量添加缓存
     *
     * @param key      键
     * @param timeout  有效时间
     * @param timeUnit 时间单位
     * @param values   值
     * @return 缓存后的长度
     */
    <T> Long setListAll(String key, long timeout, TimeUnit timeUnit, T... values);

    /**
     * 获取List结构中的值
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return List集合
     */
    <T> List<T> getList(String key, long start, long end);

    /**
     * 根据索引获取List结构中的值
     *
     * @param key   键
     * @param index 索引
     * @return 值
     */
    <T> T getListByIndex(String key, long index);

    /**
     * 获取List结构的长度
     *
     * @param key 键
     * @return 长度
     */
    Long getListSize(String key);

    /**
     * 从List结构中删除缓存
     *
     * @param key   值
     * @param count 数量
     * @param value 值
     * @return 删除后的长度
     */
    <T> Long deleteList(String key, long count, T value);

    /**
     * 向Set结构中添加缓存
     *
     * @param key    键
     * @param values 值
     * @return 缓存的数量
     */
    <T> Long setSet(String key, T... values);

    /**
     * 向Set结构中添加缓存
     *
     * @param key      键
     * @param timeout  有效时间
     * @param timeUnit 时间单位
     * @param values   值
     * @return 缓存的数量
     */
    <T> Long setSet(String key, long timeout, TimeUnit timeUnit, T... values);

    /**
     * 获取Set结构
     *
     * @param key 键
     * @return Set集合
     */
    <T> Set<T> getSet(String key);

    /**
     * 删除Set结构中的值
     *
     * @param key    键
     * @param values 值
     * @return 删除的数量
     */
    <T> Long deleteSet(String key, T... values);

    /**
     * 判断Set结构中是否有该项的值
     *
     * @param key   键
     * @param value 值
     * @return 是否存在
     */
    <T> Boolean hasSetValue(String key, T value);

    /**
     * 获取Set结构的长度
     *
     * @param key 键
     * @return 长度
     */
    Long getSetSize(String key);

    /**
     * Zset添加分数
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return {@link Double}
     */
    <T> Double incrZet(String key, T value, Double score);

    /**
     * Zset减少分数
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return {@link Double}
     */
    <T> Double decrZet(String key, T value, Double score);

    /**
     * 删除Zset中数据
     *
     * @param key    键
     * @param values 值
     * @return 删除个数
     */
    <T> Long deleteZetScore(String key, T... values);

    /**
     * zset根据分数排名获取指定元素信息
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return {@link Map<Object, Double>}
     */
    Map<Object, Double> zReverseRangeWithScore(String key, long start, long end);

    /**
     * 获取Zset指定元素分数
     *
     * @param key   键
     * @param value 值
     * @return 分数
     */
    <T> Double getZsetScore(String key, T value);

    /**
     * 获取Zset所有分数
     *
     * @param key 关键
     * @return {@link Map}
     */
    Map<Object, Double> getZsetAllScore(String key);

}