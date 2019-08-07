package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.darod.elearning.common.dto.CommentModel;
import org.darod.elearning.common.dto.CommonPageModel;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.gateway.serviceimpl.CommentServiceImpl;
import org.darod.elearning.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:29
 */
@RestController
@Api(tags = "评论接口")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/course/{courseId}/comment")
    public CommonResponse getAllCourseComment(@PathVariable("courseId") Integer courseId, @Validated CommonPageModel commonPageModel) {
        commonPageModel.checkPage();
        return ResponseUtils.getOKResponse(commentService.getAllCourseComment(courseId, commonPageModel).toJSONObject("comments"));
    }

    @GetMapping("/course/{courseId}/chapter/{chapterId}/comment")
    public CommonResponse getAllChapterComment(@PathVariable("courseId") Integer courseId,
                                               @PathVariable("chapterId") Integer chapterId,
                                               @Validated CommonPageModel commonPageModel) {
        commonPageModel.checkPage();
        return ResponseUtils.getOKResponse(commentService.getAllChapterComment(courseId, chapterId, commonPageModel).toJSONObject("comments"));
    }
    @RequiresPermissions("comment")
    @PostMapping("/course/{courseId}/comment")
    public CommonResponse addCourseComment(@PathVariable("courseId") Integer courseId,
                                           @RequestBody CommentModel commentModel) {
        commentModel.setUserId(ShiroUtils.getCurUserId());
        commentModel.setCourseId(courseId);
        return ResponseUtils.getOKResponse(commentService.addCourseComment(commentModel));
    }
    @RequiresPermissions("comment")
    @PostMapping("/course/{courseId}/chapter/{chapterId}/comment")
    public CommonResponse addChapterComment(@PathVariable("courseId") Integer courseId,
                                            @PathVariable("chapterId") Integer chapterId, @RequestBody CommentModel commentModel) {
        commentModel.setUserId(ShiroUtils.getCurUserId());
        commentModel.setCourseId(courseId);
        commentModel.setChapterId(chapterId);
        return ResponseUtils.getOKResponse(commentService.addCourseComment(commentModel));
    }

    @DeleteMapping("/course/{courseId}/comment/{commentId}")
    public CommonResponse deleteCourseComment(@PathVariable("courseId") Integer courseId,
                                              @PathVariable("commentId") Integer commentId) {
        CommentModel commentModel = new CommentModel(commentId, null, null, ShiroUtils.getCurUserId(), courseId, -1, null);
        commentService.deleteCourseComment(commentModel);
        return ResponseUtils.getOKResponse(null);
    }

    @DeleteMapping("/course/{courseId}/chapter/{chapterId}/comment/{commentId}")
    public CommonResponse deleteChapterComment(@PathVariable("courseId") Integer courseId,
                                               @PathVariable("chapterId") Integer chapterId,
                                               @PathVariable("commentId") Integer commentId) {
        CommentModel commentModel = new CommentModel(commentId, null, null, ShiroUtils.getCurUserId(), courseId, chapterId, null);
        commentService.deleteCourseComment(commentModel);
        return ResponseUtils.getOKResponse(null);
    }

}
