package org.darod.elearning.gateway.serviceimpl;

import org.darod.elearning.common.dto.ChapterModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.ChapterService;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.gateway.dao.ChapterDOMapper;
import org.darod.elearning.gateway.dao.CourseDOMapper;
import org.darod.elearning.gateway.dataobject.ChapterDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:04
 */
@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDOMapper chapterDOMapper;
    @Autowired
    CourseDOMapper courseDOMapper;

    @Override
    public List<ChapterModel> getAllChapterByCourseId(Integer courseId) {
        if (courseDOMapper.selectByPrimaryKey(courseId) == null) {
            throw new BusinessException(EmException.COURSE_NOT_EXIST);
        }
        return CopyPropertiesUtils.mapListObject(chapterDOMapper.selectAllChapterByCourseId(courseId), ChapterModel.class);
    }

    @Override
    public ChapterModel getChapterInfoById(Integer courseId, Integer chapterId) {
        ChapterDO chapterDO = chapterDOMapper.selectByPrimaryKey(chapterId);
        if (!chapterDO.getCourseId().equals(courseId)) {
            throw new BusinessException(EmException.CHAPTER_NOT_EXIST);
        }
        return CopyPropertiesUtils.copyProperties(chapterDO, ChapterModel.class);
    }

    @Override
    public List<ChapterModel> getAllChapterInfo(Integer courseId) {
        return CopyPropertiesUtils.mapListObject(chapterDOMapper.selectAllChapterByCourseId(courseId), ChapterModel.class);
    }

    @Override
    public ChapterModel addChapter(Integer courseId, ChapterModel chapterModel) {
        chapterModel.setChapterState(1);
        chapterModel.setPublishTime(new Date());
        chapterModel.setCourseId(courseId);
        return CopyPropertiesUtils.copyAndInsertThenReturn(chapterModel, ChapterDO.class, chapterDOMapper::insertSelective,
                (x) -> chapterDOMapper.selectByPrimaryKey(x.getChapterId()));
    }

    @Override
    public ChapterModel updateChapter(Integer courseId, Integer chapterId, ChapterModel chapterModel) {
        ChapterDO chapterDO = chapterDOMapper.selectByPrimaryKey(chapterId);
        if (chapterDO == null || !chapterDO.getCourseId().equals(courseId)) {
            throw new BusinessException(EmException.CHAPTER_NOT_EXIST);
        }
        chapterModel.setCourseId(courseId);
        chapterModel.setChapterId(chapterId);
        return CopyPropertiesUtils.copyAndInsertThenReturn(chapterModel, ChapterDO.class, chapterDOMapper::updateByPrimaryKeySelective,
                (x) -> chapterDOMapper.selectByPrimaryKey(x.getChapterId()));
    }

    @Override
    public void deleteChapter(Integer courseId, Integer chapterId) {
        ChapterDO chapterDO = chapterDOMapper.selectByPrimaryKey(chapterId);
        if (chapterDO == null || !chapterDO.getCourseId().equals(courseId)) {
            throw new BusinessException(EmException.CHAPTER_NOT_EXIST);
        }
        chapterDOMapper.deleteByPrimaryKey(chapterId);
    }
}
