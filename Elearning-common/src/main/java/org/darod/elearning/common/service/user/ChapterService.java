package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.ChapterModel;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:04
 */
public interface ChapterService {
    List<ChapterModel> getAllChapterByCourseId(Integer courseId);

    ChapterModel getChapterInfoById(Integer courseId, Integer chapterId);

    List<ChapterModel> getAllChapterInfo(Integer courseId);

    ChapterModel addChapter(Integer courseId,ChapterModel chapterModel);

    ChapterModel updateChapter(Integer courseId,Integer chapterId,ChapterModel chapterModel);

    void deleteChapter(Integer courseId, Integer chapterId);
}
