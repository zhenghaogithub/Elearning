package org.darod.elearning.gateway.utils;

import org.springframework.beans.BeanUtils;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/27 0027 15:12
 */
public class CopyPropertiesUtils {

    public static <T> T copyProperties(Object source, Class<T> target) {
        try {
            T t = target.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
