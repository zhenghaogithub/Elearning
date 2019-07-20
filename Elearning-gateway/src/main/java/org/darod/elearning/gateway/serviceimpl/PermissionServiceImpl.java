package org.darod.elearning.gateway.serviceimpl;

import org.darod.elearning.common.service.user.PermissionService;
import org.darod.elearning.gateway.dao.ForbidDOMapper;
import org.darod.elearning.gateway.dao.RolePrivilegeDOMapper;
import org.darod.elearning.gateway.dao.TeacherDOMapper;
import org.darod.elearning.gateway.dao.UserRoleDOMapper;
import org.darod.elearning.gateway.dataobject.TeacherDO;
import org.darod.elearning.gateway.dataobject.UserRoleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/20 0020 17:37
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    ForbidDOMapper forbidDOMapper;
    @Autowired
    RolePrivilegeDOMapper rolePrivilegeDOMapper;
    @Autowired
    UserRoleDOMapper userRoleDOMapper;
    @Autowired
    TeacherDOMapper teacherDOMapper;

    @Override
    public Set<String> getUserRoleInfo(Integer userId) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("user");  //都有用户角色
        //判断是否教师角色
        TeacherDO teacherDO = teacherDOMapper.selectByUserId(userId);
        if (teacherDO != null && teacherDO.getTeacherState().equals(0)) {
            hashSet.add("teacher");
        }
        List<UserRoleDO> userRoleInfoByUserId = userRoleDOMapper.getUserRoleInfoByUserId(userId);
        userRoleInfoByUserId.stream().forEach((x) -> hashSet.add(x.getRoleName()));
        return hashSet;
    }

    @Override
    public Set<String> getUserPermissionInfo(Integer userId) {
        List<UserRoleDO> userRoleDOS = userRoleDOMapper.getUserRoleInfoByUserId(userId);
        TeacherDO teacherDO = teacherDOMapper.selectByUserId(userId);
        if (teacherDO != null && teacherDO.getTeacherState().equals(0)) {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setRoleName("teacher");
            userRoleDOS.add(userRoleDO);
        }
//        List<RolePrivilegeDO> rolePrivilegeByRoleList = rolePrivilegeDOMapper.getRolePrivilegeByRoleList(userRoleDOS);
//        rolePrivilegeByRoleList.stream().forEach((x) -> hashSet.add(x.getPrivilegeName()));
        //去封禁列表看看被封禁的权限
        List<String> privilegeWithForbid = forbidDOMapper.getPrivilegeWithForbid(userId, userRoleDOS);
        return new HashSet<>(privilegeWithForbid);
    }


}
