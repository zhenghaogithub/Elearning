package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import org.darod.elearning.common.dto.ChapterModel;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.gateway.serviceimpl.ChapterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:06
 */
@RestController
@Api(tags = "课程章节资源接口")
public class ChapterController {
    @Autowired
    ChapterServiceImpl chapterService;

    @RequestMapping("/course/{courseId}/chapters")
    public CommonResponse getAllChapterByCourseId(@PathVariable("courseId") Integer courseId) {
        return ResponseUtils.getOKResponse(chapterService.getAllChapterByCourseId(courseId));
    }
}
