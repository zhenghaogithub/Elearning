package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.UserModel;
import org.darod.elearning.common.exception.BusinessException;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 9:19
 */
public interface UserService {
    void register(String username, String password) throws BusinessException;

    UserModel getUserById(Integer id);

//    void register(UserModel userModel) throws BusinessException;

//    UserModel validateLogin(String telphone, String encryptPassword) throws BusinessException;

    void updateUserById(UserModel userModel) throws BusinessException;

    void updateUserPasswordById(Integer userId, String oldPassword, String newPassword) throws BusinessException;

    String getChapterVideoUrl(Integer userId,Integer courseId,Integer chapterId);

//    void updateUserHeadImg(Integer userId, String url);

    String hello();
}
