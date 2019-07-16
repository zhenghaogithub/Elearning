package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.dto.UserLearnModel;
import org.darod.elearning.common.dto.UserModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.common.service.user.UserLearnService;
import org.darod.elearning.common.service.user.UserService;
import org.darod.elearning.gateway.dataobject.UserDO;
import org.darod.elearning.gateway.serviceimpl.CourseServiceImpl;
import org.darod.elearning.gateway.utils.RedisUtils;
import org.darod.elearning.gateway.utils.ShiroUtils;
import org.darod.elearning.gateway.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 14:23
 */
@RestController
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
    @Autowired
    RedisUtils redisUtils;

    private static final String OTP_PREFIX = "phone_otp:";

    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {"application/x-www-form-urlencoded"})
    @ApiOperation(value = "登录", httpMethod = "POST")
    public CommonResponse login(@RequestParam("username") String username, @RequestParam("password") String password) throws BusinessException {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(username)) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            token.setRememberMe(true); //设置是否自动登录 应该从前端传过来
            subject.login(token);
        } catch (AuthenticationException e) {
            throw new BusinessException(EmException.USER_LOGIN_FAIL);
        }
        return ResponseUtils.getOKResponse(null);
    }

    @PostMapping(value = "/user")
    @ApiOperation(value = "注册", httpMethod = "POST")
    public CommonResponse register(@RequestBody HashMap<String, String> map) throws BusinessException {
        String username = map.get("username");
        String password = map.get("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "用户名或密码格式不正确");
        }
        userService.register(username, password);
        return ResponseUtils.getOKResponse(null);
    }

    @GetMapping(value = "/user/logout")
    @ApiOperation(value = "退出登录", httpMethod = "GET")
    public CommonResponse logout(){
        ShiroUtils.logoutUser();
        return ResponseUtils.getOKResponse(null);
    }

    @PostMapping(value = "/user/otp")
    @ApiOperation(value = "获取验证码", httpMethod = "POST")
    public CommonResponse getOtp(@RequestBody HashMap<String, String> map) throws BusinessException {
        String telphone = map.get("telphone");
        if (!ValidateUtils.isPhoneNumLegal(telphone)) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "手机号格式不正确");
        }
        String otpCode = ValidateUtils.getRamdomOtp();
        redisUtils.set(OTP_PREFIX + telphone, otpCode, 300);
        //发短信给用户
        System.out.println("Telphone:" + telphone + "otpCode = " + otpCode);
        return ResponseUtils.getOKResponse(null);
    }


//    @GetMapping("/learned_course")
//    @ApiOperation(value = "获取用户已学课程", httpMethod = "GET")
//    public CommonResponse getUserLearnedCourse(@RequestParam("page") Integer page, @RequestParam("row") Integer row) throws BusinessException {
//        Integer userId = ShiroUtils.getCurUserId();
//        CommonCountModel<List<UserLearnModel>> courseLearnedInfo = userLearnService.getCourseLearnedInfo(userId, page, row);
//        return ResponseUtils.getOKResponse(courseLearnedInfo.toJSONObject("courses"));
//    }

    @GetMapping("/curuser")
    @ApiOperation(value = "获取当前用户信息", httpMethod = "GET")
    public CommonResponse getCurrentUser() throws BusinessException {
        Integer userId = ShiroUtils.getCurUserId();
        return ResponseUtils.getOKResponse(userService.getUserById(userId));
    }

    @PutMapping("/curuser")
    @ApiOperation(value = "修改当前用户信息", httpMethod = "PUT")
    public CommonResponse updateCurrentUser(@RequestBody UserModel userModel) throws BusinessException {
        Integer userId = ShiroUtils.getCurUserId();
        //清空一些不能修改的信息
        userModel.setUserId(userId);
        userModel.setEncryptPassword(null);
        userModel.setHeadUrl(null);
        userModel.setRegisterMode(null);
        userModel.setThirdPartyId(null);
        userModel.setUserState(null);
        userService.updateUserById(userModel);
        return ResponseUtils.getOKResponse(null);
    }

    @PutMapping("/curuser/password")
    @ApiOperation(value = "修改当前用户密码", httpMethod = "PUT")
    public CommonResponse updateCurrentUserPassword(@RequestBody HashMap<String, String> map) throws BusinessException {
        Integer userId = ShiroUtils.getCurUserId();
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "密码不能为空");
        }
        userService.updateUserPasswordById(userId, oldPassword, newPassword);
        ShiroUtils.logoutUser();
        return ResponseUtils.getOKResponse(null);
    }




















//    @RequestMapping("/test")
//    @RequiresRoles("1")
//    @ApiOperation(value = "测试", httpMethod = "GET")
//    public String test(@ApiParam(name = "name", value = "姓名", required = true) @RequestParam("name") String name) {
//        return userService.hello() + " " + name;
//    }

}
