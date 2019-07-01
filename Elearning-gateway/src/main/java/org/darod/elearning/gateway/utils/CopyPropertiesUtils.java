package org.darod.elearning.gateway.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.UserLearnModel;
import org.darod.elearning.gateway.dataobject.UserLearnDO;
import org.springframework.beans.BeanUtils;

import java.util.List;
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
}
