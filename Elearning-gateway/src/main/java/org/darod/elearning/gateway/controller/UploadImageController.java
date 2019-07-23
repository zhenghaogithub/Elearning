package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.darod.elearning.common.dto.ChapterModel;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.dto.LiveRoomModel;
import org.darod.elearning.common.dto.UserModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.TeacherService;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.gateway.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/19 0019 18:15
 */
@Controller
@Api(tags = "上传图片资源")
public class UploadImageController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/user/curuser/image")
    @ResponseBody
    public CommonResponse uploadUserHeadImg(@RequestParam(value = "userImageFile_path", required = false) String url) {
        UserModel userModel = new UserModel();
        userModel.setUserId(ShiroUtils.getCurUserId());
        userModel.setHeadUrl(getRealUrl("images", url));
        userService.updateUserById(userModel);
        return ResponseUtils.getOKResponse();
    }

    @PostMapping("/teachers/curTeacher/course/{courseId}/image")
    @ResponseBody
    public CommonResponse uploadCourseImg(@PathVariable("courseId") Integer courseId,
                                          @RequestParam(value = "courseImageFile_path", required = false) String url) {
        CourseModel courseModel = new CourseModel();
        courseModel.setCourseId(courseId);
        courseModel.setCourseImgUrl(getRealUrl("images", url));
        return ResponseUtils.getOKResponse(teacherService.updateCourseImageTeacher(ShiroUtils.getCurUserId(), courseModel));
    }

    @PostMapping("/teachers/curTeacher/live/image")
    @ResponseBody
    public CommonResponse uploadLiveImage(@RequestParam(value = "liveImageFile_path", required = false) String url) {
        LiveRoomModel liveRoomModel = new LiveRoomModel();
        liveRoomModel.setUserId(ShiroUtils.getCurUserId());
        liveRoomModel.setRoomImage(getRealUrl("images", url));
        return ResponseUtils.getOKResponse(teacherService.updateLiveRoom(liveRoomModel));
    }

    @PostMapping("/teachers/curTeacher/course/{courseId}/chapter/{chapterId}/video")
    @ResponseBody
    public CommonResponse uploadChapterVideo(@PathVariable("courseId") Integer courseId, @PathVariable("chapterId") Integer chapterId,
                                             @RequestParam(value = "chapterVideoFile_path", required = false) String url) {
        ChapterModel chapterModel = new ChapterModel();
        chapterModel.setVideoUrl(getRealUrl("videos", url));
        return ResponseUtils.getOKResponse(teacherService.updateChapterTeacher(ShiroUtils.getCurUserId(), courseId, chapterId, chapterModel));
    }


    private String getRealUrl(String prefix, String url) {
        if (StringUtils.isEmpty(url)) throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "URL名称或值不正确");
        int i = url.lastIndexOf(prefix);
        if (i < 0) throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "URL名称或值不正确");
        return url.substring(i);
    }

}
