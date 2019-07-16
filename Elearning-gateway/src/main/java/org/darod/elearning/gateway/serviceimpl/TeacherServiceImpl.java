package org.darod.elearning.gateway.serviceimpl;

import org.darod.elearning.common.dto.TeacherModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.TeacherService;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.gateway.dao.TeacherDOMapper;
import org.darod.elearning.gateway.dao.UserDOMapper;
import org.darod.elearning.gateway.dataobject.TeacherDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 19:19
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    TeacherDOMapper teacherDOMapper;

    @Override
    public TeacherModel addTeacherApply(TeacherModel teacherModel) {
        return null;
    }

    @Override
    public TeacherModel getTeacherInfoByTeacherId(Integer teacherId) {
        return CopyPropertiesUtils.copyProperties(teacherDOMapper.selectByPrimaryKey(teacherId), TeacherModel.class);
    }

    @Override
    public TeacherModel getTeacherInfoByUserId(Integer userId) {
        return null;
    }

    @Override
    public TeacherModel updateTeacherInfo(TeacherModel teacherModel) {
        if(teacherDOMapper.selectByUserId(teacherModel.getUserId()) == null){
            throw new BusinessException(EmException.PERMISSION_DENIED,"您不是教师");
        }
        teacherDOMapper.selectByPrimaryKey()
        TeacherDO teacherDO = CopyPropertiesUtils.copyProperties(teacherModel, TeacherDO.class);
        if(teacherModel)
        return teacherDOMapper.updateByPrimaryKeySelective(teacherModel);
    }
}
