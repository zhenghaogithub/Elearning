package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.UserLearnService;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.gateway.serviceimpl.CourseServiceImpl;
import org.darod.elearning.common.dto.CoursePageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 8:22
 */
@RestController
@Api(tags = "课程资源接口")
@RequestMapping("/course")
public class CourseController {
    @Autowired
    UserService userService;
    @Autowired
    UserLearnService userLearnService;
    @Autowired
    CourseServiceImpl courseService;

    @GetMapping("/courses")
    @ApiOperation(value = "获取所有课程", httpMethod = "GET")
    public CommonResponse getAllCourse(CoursePageModel coursePageModel){
        CommonCountModel<List<CourseModel>> allCourseInfo = courseService.getCourseInfoLimited(coursePageModel);
        return ResponseUtils.getOKResponse(allCourseInfo.toJSONObject("courses"));
    }

    @GetMapping("/courses/free")
    @ApiOperation(value = "获取所有免费课程", httpMethod = "GET")
    @Deprecated
    public CommonResponse getAllFreeCourse(CoursePageModel coursePageModel){
        CommonCountModel<List<CourseModel>> allCourseInfo = courseService.getAllCourseInfo(coursePageModel.getPage(), coursePageModel.getRow());
        return ResponseUtils.getOKResponse(allCourseInfo.toJSONObject("courses"));
    }
//    @GetMapping("/courses/free")
//    @ApiOperation(value = "获取所有免费课程", httpMethod = "GET")
//    public CommonResponse getAllFreeCourse(CoursePageModel pageModel) throws BusinessException {
//        CommonCountModel<List<CourseModel>> allCourseInfo = courseService.getAllCourseInfo(pageModel.getPage(), pageModel.getRow());
//        return ResponseUtils.getOKResponse(allCourseInfo.toJSONObject("courses"));
//    }

    @ModelAttribute
    public CoursePageModel pageModel(@Valid CoursePageModel coursePageModel)
    {
//        if (bindingResult.hasErrors()) {
//            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, bindingResult.getFieldError().getDefaultMessage());
//        }
        //如果没有指定分页信息，默认不分页
        if (coursePageModel.getPage() == null || coursePageModel.getRow() == null) {
            coursePageModel.setPage(0);
            coursePageModel.setRow(9999);
        }
        return coursePageModel;
    }
}
