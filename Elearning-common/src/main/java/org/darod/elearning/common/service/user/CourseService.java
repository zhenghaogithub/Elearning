package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.dto.CoursePageModel;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 15:24
 */
public interface CourseService {
    CommonCountModel<List<CourseModel>> getAllCourseInfo(int page, int row);

    CommonCountModel<List<CourseModel>> getAllFreeCourseInfo(int page, int row);

    CommonCountModel<List<CourseModel>> getCourseInfoLimited(CoursePageModel coursePageModel);

    CourseModel getCourseById(Integer courseId);

//    //教师视角的课程信息 目前和普通用户视角的一样
//    CourseModel getCourseToTeacherById(Integer userId, Integer courseId);
//
//    //根据用户ID找到对应的教师Id，然后找到该Id的所有课程
//    CommonCountModel<List<CourseModel>> getCourseByUserId(CoursePageModel coursePageModel);

    CourseModel addCourse(CourseModel courseModel);

    CourseModel updateCourse(CourseModel courseModel);

//    CourseModel updateCourseImage(CourseModel courseModel);

    void deleteCourse(Integer courseId);
}
