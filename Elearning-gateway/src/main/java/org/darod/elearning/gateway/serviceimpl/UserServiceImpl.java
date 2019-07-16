package org.darod.elearning.gateway.serviceimpl;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.darod.elearning.common.dto.UserModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.common.validator.ValidatorImpl;
import org.darod.elearning.gateway.dao.UserDOMapper;
import org.darod.elearning.gateway.dao.UserPasswordDOMapper;
import org.darod.elearning.gateway.dataobject.UserDO;
import org.darod.elearning.gateway.dataobject.UserPasswordDO;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 15:20
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    @Transactional
    public void register(String username, String password) throws BusinessException {
        UserDO userDO = new UserDO();
        userDO.setName(username);
        UserPasswordDO userPasswordDO = new UserPasswordDO();

        userPasswordDO.setEncryptPassword(new Md5Hash(password, username).toString());
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "用户名重复");
        }
        userPasswordDO.setUserId(userDO.getUserId());
        userPasswordDOMapper.insertSelective(userPasswordDO); //selective 只会把不为null的字段插入数据库，为null的字段会是默认值
    }

    @Override
    public UserModel getUserById(Integer userId) {
        return CopyPropertiesUtils.copyProperties(userDOMapper.selectByPrimaryKey(userId), UserModel.class);
    }

    @Override
    public void updateUserById(UserModel userModel) throws BusinessException {
        UserDO userDO = userDOMapper.selectByPrimaryKey(userModel.getUserId());
        if (userDO == null) {
            throw new BusinessException(EmException.USER_NOT_EXIST);
        }
        CopyPropertiesUtils.copyPropertiesIgnoreNull(userModel, userDO);
        validator.validateWithCheck(userDO);
        userDOMapper.updateByPrimaryKeySelective(userDO);
    }

    @Override
    public void updateUserPasswordById(Integer userId, String oldPassword, String newPassword) throws BusinessException {
        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userId);
        Md5Hash encryptPassword = new Md5Hash(oldPassword, userDO.getName());
        if (!userPasswordDO.getEncryptPassword().equals(encryptPassword.toString())) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "旧密码错误");
        }
        Md5Hash newEncryptPassword = new Md5Hash(newPassword, userDO.getName());
        userPasswordDO.setEncryptPassword(newEncryptPassword.toString());
        userPasswordDOMapper.updateByPrimaryKey(userPasswordDO);
    }

    @Override
    public String hello() {
        return "hello";
    }
}
