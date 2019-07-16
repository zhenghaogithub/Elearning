package org.darod.elearning.gateway.utils;

import org.apache.shiro.SecurityUtils;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/7 0007 20:48
 */
public class ShiroUtils {
    public static Integer getCurUserId() throws BusinessException {
        Integer userId = (Integer) SecurityUtils.getSubject().getPrincipal();
        if (userId == null) {
            throw new BusinessException(EmException.UNKNOWN_ERROR);
        }
        return userId;
    }

    public static void logoutUser() {
        SecurityUtils.getSubject().logout();
    }
}
