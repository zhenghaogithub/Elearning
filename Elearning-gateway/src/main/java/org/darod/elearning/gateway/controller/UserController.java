package org.darod.elearning.gateway.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.darod.elearning.common.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 14:23
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Reference(interfaceClass = UserService.class)
    UserService userService;

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
       return userService.hello();
    }
}
