package org.darod.elearning.gateway.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.message.ReusableMessage;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CoursePageModel;
import org.darod.elearning.common.dto.UserLearnModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.UserLearnService;
import org.darod.elearning.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/11 0011 19:41
 */
@RestController
@RequestMapping("/user/curuser")
public class UserLearnController {
    @Autowired
    private UserLearnService userLearnService;


    @GetMapping("/learned_course")
    @ApiOperation(value = "获取用户已学课程", httpMethod = "GET")
    public CommonResponse getUserLearnedCourse(@Validated CoursePageModel coursePageModel) throws BusinessException {
        //如果没有指定分页信息，默认不分页
        if (coursePageModel.getPage() == null || coursePageModel.getRow() == null) {
            coursePageModel.setPage(0);
            coursePageModel.setRow(9999);
        }
        Integer userId = ShiroUtils.getCurUserId();
        coursePageModel.setUserId(userId);
        CommonCountModel<List<UserLearnModel>> allCourseInfo = userLearnService.getCourseLearnedInfoLimited(coursePageModel, userId);
        return ResponseUtils.getOKResponse(allCourseInfo.toJSONObject("courses"));
    }

    @PostMapping("/learned_course")
    @ApiOperation(value = "学习课程", httpMethod = "POST")
    public CommonResponse addUserLearnedCourse(@Validated @RequestBody UserLearnModel userLearnModel) throws BusinessException {
        Integer curUserId = ShiroUtils.getCurUserId();
        userLearnModel.setUserId(curUserId);
        return ResponseUtils.getOKResponse(userLearnService.addUserLearnedCourse(userLearnModel));
    }


//    @ModelAttribute
//    public CoursePageModel pageModel(@Valid CoursePageModel coursePageModel)
//    {
//        //如果没有指定分页信息，默认不分页
//        if (coursePageModel.getPage() == null || coursePageModel.getRow() == null) {
//            coursePageModel.setPage(0);
//            coursePageModel.setRow(9999);
//        }
//        return coursePageModel;
//    }

}
