package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.UserModel;
import org.darod.elearning.common.exception.BusinessException;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 9:19
 */
public interface UserService {
    UserModel getUserById(int id);

    void register(UserModel userModel) throws BusinessException;

    UserModel validateLogin(String telphone, String encryptPassword) throws BusinessException;

    String hello();
}
