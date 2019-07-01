package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.UserLearnModel;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 8:29
 */
public interface UserLearnService {
    List<UserLearnModel> getCourseLearned(Integer userId);
}
