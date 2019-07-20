package org.darod.elearning.gateway.configuration;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.darod.elearning.common.service.user.PermissionService;
import org.darod.elearning.gateway.dao.UserDOMapper;
import org.darod.elearning.gateway.dao.UserPasswordDOMapper;
import org.darod.elearning.gateway.dataobject.UserDO;
import org.darod.elearning.gateway.dataobject.UserPasswordDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/30 0030 17:01
 */
public class CustomRealm extends AuthorizingRealm {
    private UserDOMapper userDOMapper;
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    PermissionService permissionService;

    @Autowired
    private void setUserMapper(UserDOMapper userDOMapper) {
        this.userDOMapper = userDOMapper;
    }

    @Autowired
    private void setUserPasswordDO(UserPasswordDOMapper userPasswordDOMapper) {
        this.userPasswordDOMapper = userPasswordDOMapper;
    }

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从数据库获取对应用户名密码的用户
        UserDO userDO = userDOMapper.selectByUserName(token.getUsername());
        if (userDO == null)
            return null;
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getUserId());
        if (userPasswordDO == null)
            return null;
        String encryptPassword = userPasswordDO.getEncryptPassword();

        if (null == encryptPassword)
            return null;
//        else if (!password.equals(new String((char[]) token.getCredentials()))) {
//            throw new AccountException("密码不正确");
//        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userDO.getUserId(), encryptPassword, getName());
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userDO.getName()));
        return simpleAuthenticationInfo;
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Integer userId = (Integer) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
//        String role = userDOMapper.selectByPrimaryKey(userId).getUserState().toString();
//        Set<String> roleSet = new HashSet<>();
//        Set<String> permissionSet = new HashSet<>();
        info.setRoles(permissionService.getUserRoleInfo(userId));
        info.setStringPermissions(permissionService.getUserPermissionInfo(userId));
        return info;
    }

    public static void main(String[] args) {
//        Md5Hash md5Hash = new Md5Hash("123456", "张三");
//        System.out.println(md5Hash.toString());
        String s = "mycorp-myproj-[\\w\\d-.]+.jar";
        System.out.println("mycorp-myproj-core.jar".matches(s));
    }
}
