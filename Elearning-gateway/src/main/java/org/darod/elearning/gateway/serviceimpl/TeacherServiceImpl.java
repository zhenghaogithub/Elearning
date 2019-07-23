package org.darod.elearning.gateway.serviceimpl;


import org.darod.elearning.common.dto.*;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.ChapterService;
import org.darod.elearning.common.service.user.CourseService;
import org.darod.elearning.common.service.user.LiveService;
import org.darod.elearning.common.service.user.TeacherService;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.gateway.annotation.WithoutPermission;
import org.darod.elearning.gateway.dao.TeacherDOMapper;
import org.darod.elearning.gateway.dao.UserDOMapper;
import org.darod.elearning.gateway.dataobject.TeacherDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 19:19
 */

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private TeacherDOMapper teacherDOMapper;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private LiveService liveService;

    //---------------------------课程信息获取--------------------------------
    @Override
    public CommonCountModel<List<CourseModel>> getCourseByUserId(CoursePageModel coursePageModel) {
        TeacherDO teacherDO = getTeacherDOByUserId(coursePageModel.getUserId());
        coursePageModel.setTeacherId(teacherDO.getTeacherId());
        return courseService.getCourseInfoLimited(coursePageModel);
//        return CommonCountModel.getCountModelFromList(PageHelper.startPage(coursePageModel.getPage(), coursePageModel.getRow()),
//                courseDOMapper.getCourseInfoLimited(coursePageModel), CourseModel.class);
    }

    @Override
    public CourseModel getCourseToTeacherById(Integer userId, Integer courseId) {
        return courseService.getCourseById(courseId);
    }

    //---------------------------教师信息管理--------------------------------
    @Override
    @WithoutPermission//不需要教师权限
    public TeacherModel addTeacherApply(TeacherModel teacherModel) {
        teacherModel.setApplyTime(new Date());
        teacherModel.setTeacherState(1);
//        TeacherDO teacherDO = CopyPropertiesUtils.copyProperties(teacherModel, TeacherDO.class);
//        teacherDOMapper.insertSelective(teacherDO);
        try {
            return CopyPropertiesUtils.copyAndInsertThenReturn(teacherModel, TeacherDO.class, teacherDOMapper::insertSelective,
                    (x) -> teacherDOMapper.selectByPrimaryKey(x.getTeacherId()));
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmException.TEACHER_HAVE_APPLY);
        }

//        return CopyPropertiesUtils.copyProperties(teacherDOMapper.selectByPrimaryKey(teacherDO.getTeacherId()), TeacherModel.class);
    }

    @Override
    @WithoutPermission//不需要教师权限
    public TeacherModel getTeacherInfoByTeacherId(Integer teacherId) {
        try {
            return CopyPropertiesUtils.copyProperties(teacherDOMapper.selectByPrimaryKey(teacherId), TeacherModel.class);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(EmException.TEACHER_NOT_EXIST);
        }
    }

    @Override
    @WithoutPermission//不需要教师权限
    public TeacherModel getTeacherInfoByUserId(Integer userId) {
        try {
            return CopyPropertiesUtils.copyProperties(teacherDOMapper.selectByUserId(userId), TeacherModel.class);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(EmException.TEACHER_NOT_EXIST);
        }
    }

    /***
     * 通过用户Id修改教师信息
     * @param teacherModel
     * @return
     */
    @Override
    public TeacherModel updateTeacherInfo(TeacherModel teacherModel) {
//        TeacherDO teacherDO = teacherDOMapper.selectByUserId(teacherModel.getUserId());
//        if (teacherDO == null) {
//            throw new BusinessException(EmException.PERMISSION_DENIED, "您不是教师");
//        } else
        teacherModel.setTeacherId(getTeacherIdByUserId(teacherModel.getUserId()));
        return CopyPropertiesUtils.copyAndInsertThenReturn(teacherModel, TeacherDO.class, teacherDOMapper::updateByPrimaryKeySelective,
                (x) -> teacherDOMapper.selectByPrimaryKey(x.getTeacherId()));
    }

    //---------------------------课程管理--------------------------------

    @Override
    public CourseModel addCourseTeacher(Integer userId, CourseModel courseModel) {
        courseModel.setTeacherId(getTeacherIdByUserId(userId));
        return courseService.addCourse(courseModel);
    }

    @Override
    public CourseModel updateCourseTeacher(Integer userId, CourseModel courseModel) {
        courseModel.setTeacherId(getTeacherIdByUserId(userId));
        return courseService.updateCourse(courseModel);
    }

    @Override
    public void deleteCourseTeacher(Integer userId, Integer courseId) {
//        //看看这个用户是不是发布这门课的老师
//        Integer teacherId = getTeacherIdByUserId(userId);
//        if (!teacherId.equals(courseService.getCourseById(courseId).getTeacherId())) {
//            throw new BusinessException(EmException.PERMISSION_DENIED, "这门课不是您发布的");
//        }
        courseService.deleteCourse(courseId);
    }

    //更新课程封面
    @Override
    public CourseModel updateCourseImageTeacher(Integer userId, CourseModel courseModel) {
        courseModel.setTeacherId(getTeacherIdByUserId(userId));
        return courseService.updateCourse(courseModel);
    }

    //---------------------------章节管理--------------------------------
    @Override
    public ChapterModel getChapterInfoByIdTeacher(Integer userId, Integer courseId, Integer chapterId) {
        return CopyPropertiesUtils.copyProperties(chapterService.getChapterInfoById(courseId, chapterId), ChapterModel.class);
    }

    @Override
    public List<ChapterModel> getAllChapterInfoTeacher(Integer userId, Integer courseId) {
        return CopyPropertiesUtils.mapListObject(chapterService.getAllChapterByCourseId(courseId), ChapterModel.class);
    }

    @Override
    public ChapterModel addChapterTeacher(Integer userId, Integer courseId, ChapterModel chapterModel) {
        return chapterService.addChapter(courseId, chapterModel);
    }

    @Override
    public ChapterModel updateChapterTeacher(Integer userId, Integer courseId, Integer chapterId, ChapterModel chapterModel) {
        return chapterService.updateChapter(courseId, chapterId, chapterModel);
    }

    @Override
    public void deleteChapterTeacher(Integer userId, Integer courseId, Integer chapterId) {
        chapterService.deleteChapter(courseId, chapterId);
    }

    //---------------------------直播相关--------------------------------
    @Override //发起直播
    public LiveRecordModel addLive(Integer userId) {
        Integer teacherId = getTeacherIdByUserId(userId);
        return liveService.addLive(teacherId);
    }

    @Override //结束直播
    public void stopLive(Integer userId) {
        Integer teacherId = getTeacherIdByUserId(userId);
        liveService.stopLive(teacherId);
    }

    @Override  //修改直播间信息
    public LiveRoomModel updateLiveRoom(LiveRoomModel liveRoomModel) {
        liveRoomModel.setTeacherId(getTeacherIdByUserId(liveRoomModel.getUserId()));
        return liveService.updateLiveRoom(liveRoomModel);
    }


    //---------------------------获取教师ID工具--------------------------------
    private TeacherDO getTeacherDOByUserId(Integer userId) {
        TeacherDO teacherDO = teacherDOMapper.selectByUserId(userId);
//        if (teacherDO == null) throw new BusinessException(EmException.TEACHER_NOT_EXIST);
        return teacherDO;
    }

    private Integer getTeacherIdByUserId(Integer userId) {
        TeacherDO teacherDO = teacherDOMapper.selectByUserId(userId);
//        if (teacherDO == null) throw new BusinessException(EmException.TEACHER_NOT_EXIST);
        return teacherDO.getTeacherId();
    }

}
