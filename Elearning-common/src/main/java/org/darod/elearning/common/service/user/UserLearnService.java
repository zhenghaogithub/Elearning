package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CoursePageModel;
import org.darod.elearning.common.dto.UserLearnModel;
import org.darod.elearning.common.exception.BusinessException;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 8:29
 */
public interface UserLearnService {
    CommonCountModel<List<UserLearnModel>> getCourseLearnedInfo(Integer userId, int page, int row);

    CommonCountModel<List<UserLearnModel>> getCourseLearnedInfoLimited(CoursePageModel coursePageModel, Integer userId);

    UserLearnModel addUserLearnedCourse(UserLearnModel userLearnModel) throws BusinessException;

}
