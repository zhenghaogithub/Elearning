package org.darod.elearning.gateway.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.dto.CoursePageModel;
import org.darod.elearning.common.dto.TeacherModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.gateway.dao.CourseDOMapper;
import org.darod.elearning.gateway.dao.TeacherDOMapper;
import org.darod.elearning.gateway.dataobject.CourseDO;
import org.darod.elearning.gateway.dataobject.TeacherDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/18 0018 12:53
 */
@Component
@Aspect
public class TeacherAdvice {
    @Autowired
    TeacherDOMapper teacherDOMapper;
    @Autowired
    CourseDOMapper courseDOMapper;

    //根据教师ID获取信息的方法不需要教师身份 获取自己教师信息的方法也不需要 因为这样就可以查看申请状态
    @Pointcut("bean(teacherServiceImpl) && @annotation(org.darod.elearning.gateway.annotation.WithoutPermission)")
    void withoutTeacherRights() {
    }

//    @Pointcut("execution(public * org.darod.elearning.gateway.serviceimpl.TeacherServiceImpl.*Chapter*(..))")
//    void chapterMethod() {
//    }

    //TeacherService下所有公共方法
    @Pointcut("bean(teacherServiceImpl) && ! withoutTeacherRights()")
    void publicTeacherMethod() {
    }

    //两种只需要检验教师身份是否合法的方法
    @Before("publicTeacherMethod() && args(coursePageModel)")
    public void checkTeacher(CoursePageModel coursePageModel) {
        checkTeacherByUserId(coursePageModel.getUserId());
    }

    @Before("publicTeacherMethod() && args(teacherModel)")
    public void checkTeacher(TeacherModel teacherModel) {
        checkTeacherByUserId(teacherModel.getUserId());
    }

    //需要检验课程Id和教师id是否匹配的方法
    @Before("publicTeacherMethod() && args(userId,courseId,..)")
    public void checkTeacher(Integer userId,Integer courseId) {
        checkTeacherByUserId(userId);
        checkTeacherAndCourse(userId,courseId);
    }

    @Before("publicTeacherMethod() && args(userId,courseModel,..)")
    public void checkTeacher(Integer userId, CourseModel courseModel) {
        checkTeacherByUserId(userId);
        if(courseModel.getCourseId() == null)return;  //方法不需要指定课程ID 比如增加课程的方法 因此不需要验证
        checkTeacherAndCourse(userId,courseModel.getCourseId());
    }
//
//    //检验课程是否由该用户发布的
//    @Before("chapterMethod() && args(userId,courseId,..)")
//    public void checkChapterUser(Integer userId, Integer courseId) {
//        checkTeacherByUserId(userId); //先检验教师身份
//        checkTeacherAndCourse(userId,courseId);
//    }

    private void checkTeacherByUserId(Integer userId) {
        TeacherDO teacherDO = teacherDOMapper.selectByUserId(userId);
        if (teacherDO == null) {
            throw new BusinessException(EmException.TEACHER_NOT_EXIST);
        } else if (teacherDO.getTeacherState() == 1) {
            throw new BusinessException(EmException.TEACHER_NOT_EXIST, "教师权限审核中");
        } else if (teacherDO.getTeacherState() == 2) {
            throw new BusinessException(EmException.PERMISSION_DENIED, "您已被封禁");
        }
    }
    private void checkTeacherAndCourse(Integer userId,Integer courseId) {
        CourseDO courseDO = courseDOMapper.selectByPrimaryKey(courseId);
        if (courseDO == null) {
            throw new BusinessException(EmException.COURSE_NOT_EXIST);
        }
        if (!teacherDOMapper.selectByUserId(userId).getTeacherId().equals(courseDO.getTeacherId())) {
            throw new BusinessException(EmException.PERMISSION_DENIED, "这门课不是您发布的");
        }
    }
}
