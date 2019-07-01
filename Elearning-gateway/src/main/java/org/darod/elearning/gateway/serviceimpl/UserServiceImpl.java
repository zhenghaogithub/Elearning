package org.darod.elearning.gateway.serviceimpl;

import org.darod.elearning.common.dto.UserModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.common.validator.ValidationResult;
import org.darod.elearning.common.validator.ValidatorImpl;
import org.darod.elearning.gateway.dao.UserDOMapper;
import org.darod.elearning.gateway.dao.UserPasswordDOMapper;
import org.darod.elearning.gateway.dataobject.UserDO;
import org.darod.elearning.gateway.dataobject.UserPasswordDO;
import org.darod.elearning.gateway.utils.CopyPropertiesUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

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
    public UserModel getUserById(int id) {
        return null;
    }

    @Override
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR);
        }
//        if (StringUtils.isEmpty(userModel.getName()) || userModel.getAge() == null ||
//                userModel.getGender() == null || StringUtils.isEmpty(userModel.getTelphone()) ||
//                StringUtils.isEmpty(userModel.getEncryptPassword())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
        ValidationResult validationResult = validator.validate(userModel);
        if (validationResult.isHasError()) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, validationResult.getErrMsg());
        }
        UserDO userDO = CopyPropertiesUtils.copyProperties(userModel,UserDO.class);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "手机号重复");
        }
        userModel.setId(userDO.getUserId());
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        BeanUtils.copyProperties(userModel, userPasswordDO);
        userPasswordDO.setUserId(userDO.getUserId());
        userPasswordDOMapper.insertSelective(userPasswordDO); //selective 只会把不为null的字段插入数据库，为null的字段会是默认值
    }

    @Override
    public UserModel validateLogin(String telphone, String encryptPassword) throws BusinessException {
        return null;
    }

    @Override
    public String hello() {
        return "hello";
    }
}
