package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.TeacherModel;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 19:19
 */
public interface TeacherService {
    TeacherModel addTeacherApply(TeacherModel teacherModel);

    TeacherModel getTeacherInfoByTeacherId(Integer teacherId);

    TeacherModel getTeacherInfoByUserId(Integer userId);

    TeacherModel updateTeacherInfo(TeacherModel teacherModel);

}
