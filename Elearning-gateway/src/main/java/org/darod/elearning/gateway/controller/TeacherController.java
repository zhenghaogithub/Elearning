package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.darod.elearning.common.dto.*;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.ChapterService;
import org.darod.elearning.common.service.user.LiveService;
import org.darod.elearning.common.service.user.TeacherService;
import org.darod.elearning.gateway.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 18:49
 */
@RestController
@Api(tags = "教师接口")
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LiveService liveService;

    @PostMapping("/curTeacher")
    @ApiOperation(value = "申请成为教师", httpMethod = "POST")
    public CommonResponse addTeacherApply(@Validated(TeacherModel.TeacherModelApply.class) @RequestBody TeacherModel teacherModel) {
        teacherModel.setUserId(ShiroUtils.getCurUserId());
        return ResponseUtils.getOKResponse(teacherService.addTeacherApply(teacherModel));
    }

    @GetMapping("/teacher/{teacherId}")
    @ApiOperation(value = "获取对应ID的教师信息", httpMethod = "GET")
    public CommonResponse getTeacherInfo(@PathVariable("teacherId") Integer teacherId) {
        TeacherModel teacherModel = teacherService.getTeacherInfoByTeacherId(teacherId);
        //不是当前教师查询时隐藏部分字段
        if (!ShiroUtils.getCurUserId().equals(teacherModel.getUserId())) {
            teacherModel.setCostPercent(null);
        }
        return ResponseUtils.getOKResponse(teacherModel);
    }

    @GetMapping("/curTeacher")
    @ApiOperation(value = "获取当前教师信息", httpMethod = "GET")
    public CommonResponse getCurTeacherInfo() {
        TeacherModel teacherModel = teacherService.getTeacherInfoByUserId(ShiroUtils.getCurUserId());
        return ResponseUtils.getOKResponse(teacherModel);
    }

    @PutMapping("/curTeacher")
    @ApiOperation(value = "修改当前教师信息", httpMethod = "PUT")
    public CommonResponse updateTeacherInfo(@Validated(TeacherModel.TeacherModelUpdate.class) @RequestBody TeacherModel teacherModel) {
        teacherModel.setUserId(ShiroUtils.getCurUserId());
        return ResponseUtils.getOKResponse(teacherService.updateTeacherInfo(teacherModel));
    }

    //课程管理相关
    @GetMapping("/curTeacher/course")
    @ApiOperation(value = "获取当前教师发布的课程", httpMethod = "GET")
    public CommonResponse getAllCourseInfo(@Validated CoursePageModel coursePageModel) {
        coursePageModel.checkPage();
        coursePageModel.setUserId(ShiroUtils.getCurUserId());
        return ResponseUtils.getOKResponse(teacherService.getCourseByUserId(coursePageModel).toJSONObject("courses"));
    }

    @GetMapping("/curTeacher/course/{courseId}")
    @ApiOperation(value = "获取当前教师发布的某一ID的课程", httpMethod = "GET")
    public CommonResponse getCourseInfoById(@PathVariable("courseId") Integer courseId) {
        return ResponseUtils.getOKResponse(teacherService.getCourseToTeacherById(ShiroUtils.getCurUserId(), courseId));
    }

    @PostMapping("/curTeacher/course")
    @RequiresPermissions("add_course")
    @ApiOperation(value = "发布新的课程", httpMethod = "POST")
    public CommonResponse addCourse(@Validated(CourseModel.CourseModelForCreate.class) @RequestBody CourseModel courseModel) {
        return ResponseUtils.getOKResponse(teacherService.addCourseTeacher(ShiroUtils.getCurUserId(), courseModel));
    }

    @PutMapping("/curTeacher/course")//id写在json里吧 省的再拼URL
    @ApiOperation(value = "修改课程信息", httpMethod = "PUT")
    public CommonResponse updateCourse(@Validated(CourseModel.CourseModelForUpdate.class) CourseModel courseModel) {
        return ResponseUtils.getOKResponse(teacherService.updateCourseTeacher(ShiroUtils.getCurUserId(), courseModel));
    }

    @DeleteMapping("/curTeacher/course/{courseId}")//id写在json里吧 省的再拼URL
    @ApiOperation(value = "删除课程", httpMethod = "DELETE")
    public CommonResponse deleteCourse(@PathVariable("courseId") Integer courseId) {
        teacherService.deleteCourseTeacher(ShiroUtils.getCurUserId(), courseId);
        return ResponseUtils.getOKResponse();
    }

    //章节管理相关
    @GetMapping("/curTeacher/course/{courseId}/chapter/{chapterId}")
    @ApiOperation(value = "获取课程的某一个章节", httpMethod = "GET")
    public CommonResponse getChapterInfoById(@PathVariable("courseId") Integer courseId, @PathVariable("chapterId") Integer chapterId) {
        return ResponseUtils.getOKResponse(teacherService.getChapterInfoByIdTeacher(ShiroUtils.getCurUserId(), courseId, chapterId));
    }

    @GetMapping("/curTeacher/course/{courseId}/chapter")
    @ApiOperation(value = "获取课程的全部章节", httpMethod = "GET")
    public CommonResponse getAllChapterInfo(@PathVariable("courseId") Integer courseId) {
        return ResponseUtils.getOKResponse(teacherService.getAllChapterInfoTeacher(ShiroUtils.getCurUserId(), courseId));
    }

    @PostMapping("/curTeacher/course/{courseId}/chapter")
    @RequiresPermissions("add_course")
    @ApiOperation(value = "添加章节", httpMethod = "POST")
    public CommonResponse addChapter(@PathVariable("courseId") Integer courseId,
                                     @Validated(ChapterModel.ChapterModelForCreate.class) @RequestBody ChapterModel chapterModel) {
        return ResponseUtils.getOKResponse(teacherService.addChapterTeacher(ShiroUtils.getCurUserId(), courseId, chapterModel));
    }

    @PutMapping("/curTeacher/course/{courseId}/chapter/{chapterId}")
    @RequiresPermissions("add_course")
    @ApiOperation(value = "修改章节", httpMethod = "PUT")
    public CommonResponse updateChapter(@PathVariable("courseId") Integer courseId, @PathVariable("chapterId") Integer chapterId,
                                        @Validated(ChapterModel.ChapterModelForUpdate.class) @RequestBody ChapterModel chapterModel) {
        return ResponseUtils.getOKResponse(teacherService.updateChapterTeacher(ShiroUtils.getCurUserId(), courseId, chapterId, chapterModel));
    }

    @DeleteMapping("/curTeacher/course/{courseId}/chapter/{chapterId}")
    @ApiOperation(value = "删除章节", httpMethod = "DELETE")
    public CommonResponse deleteChapter(@PathVariable("courseId") Integer courseId, @PathVariable("chapterId") Integer chapterId) {
        teacherService.deleteChapterTeacher(ShiroUtils.getCurUserId(), courseId, chapterId);
        return ResponseUtils.getOKResponse();
    }

    @PostMapping("/curTeacher/live")
    @RequiresPermissions("add_live")
    @ApiOperation(value = "申请发起直播", httpMethod = "POST")
    public CommonResponse addLive() {
        return ResponseUtils.getOKResponse(teacherService.addLive(ShiroUtils.getCurUserId()));
    }

    @DeleteMapping("/curTeacher/live")
    @ApiOperation(value = "结束直播", httpMethod = "DELETE")
    public CommonResponse stopLive() {
        teacherService.stopLive(ShiroUtils.getCurUserId());
        return ResponseUtils.getOKResponse();
    }

    @PutMapping("/curTeacher/liveRoom")
    @RequiresPermissions("add_live")
    @ApiOperation(value = "修改直播间信息", httpMethod = "PUT")
    public CommonResponse updateLiveRoom(@RequestBody Map<String, String> map) {
        LiveRoomModel liveRoomModel = new LiveRoomModel();
        liveRoomModel.setUserId(ShiroUtils.getCurUserId());
        liveRoomModel.setRoomDescription(map.get("roomDescription"));
        liveRoomModel.setRoomName(map.get("roomName"));
        return ResponseUtils.getOKResponse(teacherService.updateLiveRoom(liveRoomModel));
    }


}
