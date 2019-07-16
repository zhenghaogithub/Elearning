package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.dto.TeacherModel;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.ChapterService;
import org.darod.elearning.gateway.serviceimpl.ChapterServiceImpl;
import org.darod.elearning.gateway.serviceimpl.CourseServiceImpl;
import org.darod.elearning.gateway.serviceimpl.TeacherServiceImpl;
import org.darod.elearning.gateway.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    TeacherServiceImpl teacherService;
    @Autowired
    CourseServiceImpl courseService;
    @Autowired
    ChapterServiceImpl chapterService;

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
    public CommonResponse updateTeacherInfo(@Validated(TeacherModel.TeacherModelApply.class) TeacherModel teacherModel) {

    }

    //课程管理相关
    @GetMapping("/curTeacher/course")
    @ApiOperation(value = "获取当前教师发布的课程", httpMethod = "GET")
    public CommonResponse getAllCourseInfo() {

    }

    @GetMapping("/curTeacher/course/{courseId}")
    @ApiOperation(value = "获取当前教师发布的课程", httpMethod = "GET")
    public CommonResponse getCourseInfoById(@PathVariable("courseId") Integer courseId) {

    }

    @PostMapping("/curTeacher/course")
    @ApiOperation(value = "发布新的课程", httpMethod = "POST")
    public CommonResponse addCourse(@Validated CourseModel courseModel) {

    }

    @PutMapping("/curTeacher/course")//id写在json里吧 省的再拼URL
    @ApiOperation(value = "修改课程信息", httpMethod = "PUT")
    public CommonResponse updateCourse(@Validated(CourseModel.CourseModelForCreate.class) CourseModel courseModel) {

    }

    @DeleteMapping("/curTeacher/course/{courseId}")//id写在json里吧 省的再拼URL
    @ApiOperation(value = "删除课程", httpMethod = "DELETE")
    public CommonResponse deleteCourse(@PathVariable("courseId") Integer courseId) {

    }

    //章节管理相关
    @GetMapping("/curTeacher/course/{courseId}/chapter/{chapterId}")
    @ApiOperation(value = "获取课程的某一个章节", httpMethod = "GET")
    public CommonResponse getChapterInfoById(@PathVariable("courseId") Integer courseId, @PathVariable("chapterId") Integer chapterId) {

    }

    @GetMapping("/curTeacher/course/{courseId}/chapter")
    @ApiOperation(value = "获取课程的全部章节", httpMethod = "GET")
    public CommonResponse getAllChapterInfo(@PathVariable("courseId") Integer courseId) {

    }

    @PostMapping("/curTeacher/course/{courseId}/chapter")
    @ApiOperation(value = "添加章节", httpMethod = "POST")
    public CommonResponse addChapter(@PathVariable("courseId") Integer courseId) {

    }

    @PutMapping("/curTeacher/course/{courseId}/chapter")
    @ApiOperation(value = "修改章节", httpMethod = "PUT")
    public CommonResponse updateChapter(@PathVariable("courseId") Integer courseId) {

    }

    @DeleteMapping("/curTeacher/course/{courseId}/chapter/{chapterId}")
    @ApiOperation(value = "删除章节", httpMethod = "DELETE")
    public CommonResponse updateChapter(@PathVariable("courseId") Integer courseId, @PathVariable("chapterId") Integer chapterId) {

    }


}
