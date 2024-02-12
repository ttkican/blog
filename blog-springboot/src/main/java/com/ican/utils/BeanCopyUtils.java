package com.ican.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 拷贝工具
 *
 * @author ican
 */
@Slf4j
public class BeanCopyUtils {

    public static <T> T copyBean(Object source, Class<T> target) {
        // 创建目标对象
        T result = null;
        try {
            result = target.getDeclaredConstructor().newInstance();
            if (source != null) {
                // 实现属性copy
                BeanUtils.copyProperties(source, result);
            }
        } catch (Exception e) {
            log.error("copyBean is error, {}", e.getMessage());
        }
        // 返回结果
        return result;
    }

    public static <T, S> List<T> copyBeanList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && !source.isEmpty()) {
            for (Object obj : source) {
                list.add(copyBean(obj, target));
            }
        }
        return list;
    }

}
