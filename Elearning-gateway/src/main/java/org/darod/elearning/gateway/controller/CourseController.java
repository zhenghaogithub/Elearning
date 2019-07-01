package org.darod.elearning.gateway.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.UserLearnService;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.gateway.dataobject.UserDO;
import org.darod.elearning.gateway.serviceimpl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.Subject;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 8:22
 */
@RestController
@Api(tags = "课程资源接口")
public class CourseController {
    @Autowired
    UserService userService;
    @Autowired
    UserLearnService userLearnService;
    @Autowired
    CourseServiceImpl courseService;

    @GetMapping("/course")
    @ApiOperation(value = "获取所有课程", httpMethod = "GET")
    public CommonResponse getAllCourse(@RequestParam("page") Integer page, @RequestParam("row") Integer row) throws BusinessException {
        CommonCountModel<List<CourseModel>> allCourseInfo = courseService.getAllCourseInfo(page, row);
        return ResponseUtils.getOKResponse(allCourseInfo.toJSONObject("courses"));
    }
}
