package org.darod.elearning.gateway.serviceimpl;

import org.darod.elearning.common.dto.ChapterModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.ChapterService;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.gateway.dao.ChapterDOMapper;
import org.darod.elearning.gateway.dao.CourseDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
