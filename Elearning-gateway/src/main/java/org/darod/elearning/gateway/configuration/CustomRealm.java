package org.darod.elearning.gateway.configuration;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
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
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        if (userPasswordDO == null)
            return null;
        String encryptPassword = userPasswordDO.getEncryptPassword();

        if (null == encryptPassword)
            return null;
//        else if (!password.equals(new String((char[]) token.getCredentials()))) {
//            throw new AccountException("密码不正确");
//        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(token.getPrincipal(), encryptPassword, getName());
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
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = userDOMapper.selectByUserName(username).getUserState().toString();
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        set.add(role);
        //设置该用户拥有的角色
        info.setRoles(set);
        return info;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456", "张三");
        System.out.println(md5Hash.toString());
    }
}
