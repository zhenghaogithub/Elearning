package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.*;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 19:19
 */
public interface TeacherService {

    CommonCountModel<List<CourseModel>> getCourseByUserId(CoursePageModel coursePageModel);

    CourseModel getCourseToTeacherById(Integer userId, Integer courseId);

    TeacherModel addTeacherApply(TeacherModel teacherModel);

    TeacherModel getTeacherInfoByTeacherId(Integer teacherId);

    TeacherModel getTeacherInfoByUserId(Integer userId);

    TeacherModel updateTeacherInfo(TeacherModel teacherModel);

    CourseModel addCourseTeacher(Integer userId, CourseModel courseModel);

    CourseModel updateCourseTeacher(Integer userId, CourseModel courseModel);

    CourseModel updateCourseImageTeacher(Integer userId, CourseModel courseModel);

    void deleteCourseTeacher(Integer userId, Integer courseId);

    ChapterModel getChapterInfoByIdTeacher(Integer userId, Integer courseId, Integer chapterId);

    List<ChapterModel> getAllChapterInfoTeacher(Integer userId, Integer courseId);

    ChapterModel addChapterTeacher(Integer userId, Integer courseId, ChapterModel chapterModel);

    ChapterModel updateChapterTeacher(Integer userId, Integer courseId, Integer chapterId, ChapterModel chapterModel);

    void deleteChapterTeacher(Integer userId, Integer courseId, Integer chapterId);

}
