package org.darod.elearning.gateway.serviceimpl;

import com.github.pagehelper.PageHelper;
import org.darod.elearning.common.dto.CommentModel;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CommonPageModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.CommentService;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.gateway.dao.ChapterDOMapper;
import org.darod.elearning.gateway.dao.CommentDOMapper;
import org.darod.elearning.gateway.dao.CourseDOMapper;
import org.darod.elearning.gateway.dao.UserLearnDOMapper;
import org.darod.elearning.gateway.dataobject.CommentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:41
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDOMapper commentDOMapper;
    @Autowired
    CourseDOMapper courseDOMapper;
    @Autowired
    ChapterDOMapper chapterDOMapper;
    @Autowired
    UserLearnDOMapper userLearnDOMapper;

    @Override
    public CommonCountModel<List<CommentModel>> getAllCourseComment(Integer courseId, CommonPageModel commonPageModel) {
        return CommonCountModel.getCountModelFromList(PageHelper.startPage(commonPageModel.getPage(), commonPageModel.getRow()),
                commentDOMapper.selectCourseCommentByCourseId(courseId), CommentModel.class);
    }

    @Override
    public CommonCountModel<List<CommentModel>> getAllChapterComment(Integer courseId, Integer chapterId, CommonPageModel commonPageModel) {
        return CommonCountModel.getCountModelFromList(PageHelper.startPage(commonPageModel.getPage(), commonPageModel.getRow()),
                commentDOMapper.selectChapterCommentByCourseIdAndChapterId(courseId, chapterId), CommentModel.class);
    }

    @Override
    public CommentModel addCourseComment(CommentModel commentModel) {
        //没有章节号 则添加到课程评论 否则作为章节评论
        if (commentModel.getChapterId() == null) {
            commentModel.setChapterId(-1);
        }
        //看看用户学习了这门课程了吗
        if (userLearnDOMapper.selectByUserIdAndCourseId(commentModel.getUserId(), commentModel.getCourseId()) == null) {
            throw new BusinessException(EmException.COURSE_NOT_LEARNED);
        }
        //看看有没有这个章节
        if (commentModel.getChapterId() != -1 && chapterDOMapper.selectByPrimaryKey(commentModel.getChapterId()) == null) {
            throw new BusinessException(EmException.CHAPTER_NOT_EXIST);
        }
        return CopyPropertiesUtils.copyAndInsertThenReturn(commentModel,CommentDO.class,(commentDO) -> {
            commentDO.setCommentState(0);
            commentDO.setCommentId(null);
            commentDO.setCommentTime(new Date());
            commentDOMapper.insertSelective(commentDO);
        },(x)->commentDOMapper.selectByPrimaryKey(x.getCommentId()));
//        CommentDO commentDO = CopyPropertiesUtils.copyProperties(commentModel, CommentDO.class);
//        commentDO.setCommentState(0);
//        commentDO.setCommentId(null);
//        commentDO.setCommentTime(new Date());
//        commentDOMapper.insertSelective(commentDO);
//        return CopyPropertiesUtils.copyProperties(commentDOMapper.selectByPrimaryKey(commentDO.getCommentId()), CommentModel.class);
    }

    @Override
    public void deleteCourseComment(CommentModel commentModel) {
        CommentDO commentDO = commentDOMapper.selectByPrimaryKey(commentModel.getCommentId());
        if (commentDO == null) throw new BusinessException(EmException.COMMENT_NOT_EXIST);
        if (!commentDO.getUserId().equals(commentModel.getUserId()))
            throw new BusinessException(EmException.PERMISSION_DENIED, "该评论不是你发布的");
        if (!(commentDO.getChapterId().equals(commentModel.getChapterId()) && commentDO.getCourseId().equals(commentModel.getCourseId())))
            throw new BusinessException(EmException.COMMENT_NOT_EXIST);
        commentDOMapper.deleteByPrimaryKey(commentModel.getCommentId());
    }
}
