package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.CommentModel;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CommonPageModel;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:31
 */
public interface CommentService {

    CommonCountModel<List<CommentModel>> getAllCourseComment(Integer courseId, CommonPageModel commonPageModel);

    CommonCountModel<List<CommentModel>> getAllChapterComment(Integer courseId, Integer chapterId, CommonPageModel commonPageModel);

    CommentModel addCourseComment(CommentModel commentModel);

    void deleteCourseComment(CommentModel commentModel);
}
