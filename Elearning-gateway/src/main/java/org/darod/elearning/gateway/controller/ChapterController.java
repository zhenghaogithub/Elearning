package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import org.darod.elearning.common.dto.ChapterModel;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.ChapterService;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.gateway.serviceimpl.ChapterServiceImpl;
import org.darod.elearning.gateway.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:06
 */
@Controller
@Api(tags = "课程章节资源接口")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private UserService userService;

    @RequestMapping("/course/{courseId}/chapters")
    @ResponseBody
    public CommonResponse getAllChapterByCourseId(@PathVariable("courseId") Integer courseId) {
        return ResponseUtils.getOKResponse(chapterService.getAllChapterByCourseId(courseId));
    }

    //获取章节视频
    @RequestMapping("/course/{courseId}/chapter/{chapterId}/video")
    public void getChapterVideoHeader(@PathVariable("courseId") Integer courseId,
                                                @PathVariable("chapterId") Integer chapterId,HttpServletRequest request, HttpServletResponse response) {
        String chapterVideoUrl = userService.getChapterVideoUrl(ShiroUtils.getCurUserId(), courseId, chapterId);
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("X-Accel-Redirect", "/" + chapterVideoUrl);
        response.setHeader("X-Accel-Charset", "utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + chapterVideoUrl);
        response.setHeader("Cache-Control", "no-store"); //禁止浏览器缓存 这个可以用在试看视频上  完整视频可以缓存
    }


}
