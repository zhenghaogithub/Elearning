package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.UserLearnService;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.gateway.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.Subject;

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

    @GetMapping("/user/learned_course")
    @ApiOperation(value = "获取用户已学课程",httpMethod = "GET")
    public CommonResponse getUserLearnedCourse() throws BusinessException {
        UserDO userDO = (UserDO)SecurityUtils.getSubject().getPrincipal();
        if(userDO == null){
            throw new BusinessException(EmException.UNKNOWN_ERROR);
        }
        return ResponseUtils.getOKResponse(userLearnService.getCourseLearned(userDO.getId()));
    }
}
