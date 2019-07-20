package org.darod.elearning.gateway.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/19 0019 23:20
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WithoutPermission {
    //标记不需要权限验证的方法
}
