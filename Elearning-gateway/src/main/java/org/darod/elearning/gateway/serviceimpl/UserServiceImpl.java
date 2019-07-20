package org.darod.elearning.gateway.serviceimpl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.darod.elearning.common.dto.ChapterModel;
import org.darod.elearning.common.dto.UserModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.ChapterService;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.common.validator.ValidatorImpl;
import org.darod.elearning.gateway.dao.ChapterDOMapper;
import org.darod.elearning.gateway.dao.UserDOMapper;
import org.darod.elearning.gateway.dao.UserLearnDOMapper;
import org.darod.elearning.gateway.dao.UserPasswordDOMapper;
import org.darod.elearning.gateway.dataobject.ChapterDO;
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
    @Autowired
    private UserLearnDOMapper userLearnDOMapper;
    @Autowired
    ChapterService chapterService;

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

//    @Override
//    public void updateUserHeadImg(Integer userId, String url) {
//        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
//        if (userDO == null) {
//            throw new BusinessException(EmException.USER_NOT_EXIST);
//        }
//        UserDO userDO_new = new UserDO();  //新建一个用来更新 防止其它字段的更新被覆盖
//        userDO.setUserId(userId);
//        userDO.setHeadUrl(url);
//        userDOMapper.updateByPrimaryKeySelective(userDO_new);
//    }


    @Override
    public String getChapterVideoUrl(Integer userId, Integer courseId, Integer chapterId) {
        //看看用户学习了这门课程了吗
        if (userLearnDOMapper.selectByUserIdAndCourseId(userId, courseId) == null) {
            throw new BusinessException(EmException.COURSE_NOT_LEARNED);
        }
        ChapterModel chapterInfoById = chapterService.getChapterInfoById(courseId, chapterId);
        if(StringUtils.isEmpty(chapterInfoById.getVideoUrl())){
            throw  new BusinessException(EmException.CHAPTER_VIDEO_NOT_EXIST);
        }
        return chapterInfoById.getVideoUrl();
    }

    @Override
    public String hello() {
        return "hello";
    }
}
