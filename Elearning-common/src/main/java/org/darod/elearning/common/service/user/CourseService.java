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
}
