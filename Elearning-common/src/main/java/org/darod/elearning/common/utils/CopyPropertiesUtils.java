package org.darod.elearning.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static <T> List<T> mapListObject(List<?> source, Class<T> target) {
        return source.stream().map(item -> {
            T t = copyProperties(item, target);
            return t;
        }).collect(Collectors.toList());
    }

    public static <T,R> List<T> mapListObjectWithMapper(List<R> source, Class<T> target, Function<R, T> mapper) {
        return source.stream().map(item -> {
            return mapper.apply(item);
//            T t = copyProperties(item, target);
//            return t;
        }).collect(Collectors.toList());
    }

    //复制Bean属性 忽略null值
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    //获取Bean为null的属性名
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
