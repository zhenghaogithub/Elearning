package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.darod.elearning.common.dto.UserModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EnumBusinessException;
import org.darod.elearning.common.response.CommonReturnType;
import org.darod.elearning.common.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.UnsupportedEncodingException;

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
    HttpServletRequest httpServletRequest;

    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {"application/x-www-form-urlencoded"})
    public CommonReturnType login(@RequestParam("telphone") String telphone, @RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(telphone)) {
            throw new BusinessException(EnumBusinessException.PARAMETER_VALIDATION_ERROR);
        }
        //验证密码
        UserModel userModel = userService.validataLogin(telphone, EncodeByMD5(password));
        //没有异常 则登录成功
        httpServletRequest.getSession().setAttribute("LOGIN", true);
        httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
        return CommonReturnType.create(null);
    }

    private String EncodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }


    @ResponseBody
    @RequestMapping("/test")
    @ApiOperation(value = "测试", httpMethod = "GET")
    public String test(@ApiParam(name = "name", value = "姓名", required = true) @RequestParam("name") String name) {
        return userService.hello() + " " + name;
    }

}
