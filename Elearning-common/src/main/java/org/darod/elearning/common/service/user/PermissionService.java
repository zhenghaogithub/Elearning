package org.darod.elearning.common.service.user;

import java.util.Set;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/20 0020 17:36
 */
public interface PermissionService {

    Set<String> getUserRoleInfo(Integer userId);

    Set<String> getUserPermissionInfo(Integer userId);

}
