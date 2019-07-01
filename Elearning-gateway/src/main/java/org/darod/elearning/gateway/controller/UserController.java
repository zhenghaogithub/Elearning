package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.dto.UserModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.UserLearnService;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.gateway.dataobject.UserDO;
import org.darod.elearning.gateway.serviceimpl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 14:23
 */
@Controller
@RequestMapping("/user")
@Api(tags = "用户资源接口")
public class UserController {
    //    @Reference(interfaceClass = UserService.class)
    @Autowired
    UserService userService;
    @Autowired
    UserLearnService userLearnService;
    @Autowired
    CourseServiceImpl courseService;
    @Autowired
    HttpServletRequest httpServletRequest;

    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {"application/x-www-form-urlencoded"})
    @ApiOperation(value = "登录", httpMethod = "POST")
    public CommonResponse login(@RequestParam("username") String username, @RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(username)) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            throw new BusinessException(EmException.USER_LOGIN_FAIL);
        }
        return ResponseUtils.getOKResponse(null);
    }

    @GetMapping("/learned_course")
    @ApiOperation(value = "获取用户已学课程", httpMethod = "GET")
    public CommonResponse getUserLearnedCourse() throws BusinessException {
        UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
        if (userDO == null) {
            throw new BusinessException(EmException.UNKNOWN_ERROR);
        }

//        return ResponseUtils.getOKResponse(userLearnService.getCourseLearned(userDO.getId()));
        return null;
    }


    @ResponseBody
    @RequestMapping("/test")
    @ApiOperation(value = "测试", httpMethod = "GET")
    public String test(@ApiParam(name = "name", value = "姓名", required = true) @RequestParam("name") String name) {
        return userService.hello() + " " + name;
    }

}
