package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import org.darod.elearning.common.dto.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/25 0025 8:24
 */
//@RequestMapping("/all")
@Controller
@Api(tags = "测试资源")
public class TestController {

    @RequestMapping(value = "/api/user/user/getUserCurrent", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getUserInfo() {
        return "{\n" +
                "\t\"status\":200,\n" +
                "\t\"message\":\"ok\",\n" +
                "\t\"data\":{\n" +
                "\t\t\"userId\":1,\n" +
                "\t\t\"username\": \"张三\", \n" +
                "\t\t\"userIntroduction\":\"没有\", \n" +
                "\t\t\"age\":13,\n" +
                "\t\t\"phone\":\"12345678912\",\n" +
                "\t\t\"email\":\"123@zxc.com\",\n" +
                "\t\t\"userImage\":\"resources/test_head_2.png\", \n" +
                "\t\t\"power\":2,\n" +
                "\t\t\"gender\":\"男\"\n" +
                "\t}\n" +
                "}\n";
    }

    @RequestMapping(value = "/api/user/learn/selectLearnByUserId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCourseLearned() {
//        return "{\n" +
//                "\t\"status\":200,\n" +
//                "\t\"message\":\"ok\",\n" +
//                "\t\"data\":{\n" +
//                "\t\t\"total\":1,\n" +
//                "\t\t\"courses\":[{\n" +
//                "\t\t\t\"courseId\":1,\n" +
//                "\t\t\t\"courseName\":\"ASP.NET-MVC网站开发【实战与技能详解】\",\n" +
//                "\t\t\t\"courseImage\":\"https://10.url.cn/qqcourse_logo_ng/ajNVdqHZLLC08B9HDxI6BrAyznCvqVuyYabPqAElDJrJlorKZy8iamBvsbHrJElh8yRvQt6nicaXc/356?tp=webp\",\n" +
//                "\t\t\t\"cost\":5,\n" +
//                "\t\t\t\"courseIntroduction\":\"没有\",\n" +
//                "\t\t\t\"labelFirst\":\"后端开发\",\n" +
//                "\t\t\t\"labelSecond\":\"网站搭建\",\n" +
//                "\t\t\t\"labelThird\":\".net\",\n" +
//                "\t\t\t\"courseState\":0, \n" +
//                "\t\t\t\"courseTime\":0,\n" +
//                "\t\t\t\"learnTotal\":33,\n" +
//                "\t\t\t\"teacherId\":1,\n" +
//                "\t\t\t\"career\":\"讲师\",\n" +
//                "\t\t\t\"teacherIntroduction\":\"没有\",\n" +
//                "\t\t\t\"teacherName\":\"罗老师\",\n" +
//                "\t\t\t\"learnId\":1,\n" +
//                "\t\t\t\"userId\":1,\n" +
//                "\t\t\t\"learnTime\":\"2019-01-23\",\n" +
//                "\t\t\t\"lastChapterNumber\":1,\n" +
//                "\t\t\t\"lastChapterName\":\"路由匹配原理分析与多个路由定义规范\",\n" +
//                "\t\t\t\"orderState\":0\n" +
//                "\t\t\t}\n" +
//                "\t\t]\n" +
//                "\t}\n" +
//                "}\n";
        return "{\n" +
                "\t\"status\":200,\n" +
                "\t\"message\":\"ok\",\n" +
                "\t\"data\":{\n" +
                "\t\t\"total\":4,\n" +
                "\t\t\"courses\":[{\n" +
                "\t\t\t\"courseId\":1,\n" +
                "\t\t\t\"courseName\":\"课程1\",\n" +
                "\t\t\t\"courseImage\":\"https://10.url.cn/qqcourse_logo_ng/ajNVdqHZLLC08B9HDxI6BrAyznCvqVuyYabPqAElDJrJlorKZy8iamBvsbHrJElh8yRvQt6nicaXc/356?tp=webp\",\n" +
                "\t\t\t\"cost\":5,\n" +
                "\t\t\t\"courseIntroduction\":\"没有\",\n" +
                "\t\t\t\"labelFirst\":\"后端开发\",\n" +
                "\t\t\t\"labelSecond\":\"网站搭建\",\n" +
                "\t\t\t\"labelThird\":\".net\",\n" +
                "\t\t\t\"courseState\":0, \n" +
                "\t\t\t\"courseTime\":0,\n" +
                "\t\t\t\"learnTotal\":33,\n" +
                "\t\t\t\"teacherId\":1,\n" +
                "\t\t\t\"career\":\"讲师\",\n" +
                "\t\t\t\"teacherIntroduction\":\"没有\",\n" +
                "\t\t\t\"teacherName\":\"罗老师\",\n" +
                "\t\t\t\"learnId\":1,\n" +
                "\t\t\t\"userId\":1,\n" +
                "\t\t\t\"learnTime\":\"2019-01-23\",\n" +
                "\t\t\t\"lastChapterNumber\":1,\n" +
                "\t\t\t\"lastChapterName\":\"路由匹配原理分析与多个路由定义规范\",\n" +
                "\t\t\t\"orderState\":0\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\"courseId\":1,\n" +
                "\t\t\t\"courseName\":\"ASP.NET-MVC网站开发【实战与技能详解】\",\n" +
                "\t\t\t\"courseImage\":\"https://10.url.cn/qqcourse_logo_ng/ajNVdqHZLLC08B9HDxI6BrAyznCvqVuyYabPqAElDJrJlorKZy8iamBvsbHrJElh8yRvQt6nicaXc/356?tp=webp\",\n" +
                "\t\t\t\"cost\":5,\n" +
                "\t\t\t\"courseIntroduction\":\"没有\",\n" +
                "\t\t\t\"labelFirst\":\"后端开发\",\n" +
                "\t\t\t\"labelSecond\":\"网站搭建\",\n" +
                "\t\t\t\"labelThird\":\".net\",\n" +
                "\t\t\t\"courseState\":0, \n" +
                "\t\t\t\"courseTime\":0,\n" +
                "\t\t\t\"learnTotal\":33,\n" +
                "\t\t\t\"teacherId\":1,\n" +
                "\t\t\t\"career\":\"讲师\",\n" +
                "\t\t\t\"teacherIntroduction\":\"没有\",\n" +
                "\t\t\t\"teacherName\":\"罗老师\",\n" +
                "\t\t\t\"learnId\":1,\n" +
                "\t\t\t\"userId\":1,\n" +
                "\t\t\t\"learnTime\":\"2019-01-23\",\n" +
                "\t\t\t\"lastChapterNumber\":1,\n" +
                "\t\t\t\"lastChapterName\":\"路由匹配原理分析与多个路由定义规范\",\n" +
                "\t\t\t\"orderState\":0\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\"courseId\":1,\n" +
                "\t\t\t\"courseName\":\"ASP.NET-MVC网站开发【实战与技能详解】\",\n" +
                "\t\t\t\"courseImage\":\"https://10.url.cn/qqcourse_logo_ng/ajNVdqHZLLC08B9HDxI6BrAyznCvqVuyYabPqAElDJrJlorKZy8iamBvsbHrJElh8yRvQt6nicaXc/356?tp=webp\",\n" +
                "\t\t\t\"cost\":5,\n" +
                "\t\t\t\"courseIntroduction\":\"没有\",\n" +
                "\t\t\t\"labelFirst\":\"后端开发\",\n" +
                "\t\t\t\"labelSecond\":\"网站搭建\",\n" +
                "\t\t\t\"labelThird\":\".net\",\n" +
                "\t\t\t\"courseState\":0, \n" +
                "\t\t\t\"courseTime\":0,\n" +
                "\t\t\t\"learnTotal\":33,\n" +
                "\t\t\t\"teacherId\":1,\n" +
                "\t\t\t\"career\":\"讲师\",\n" +
                "\t\t\t\"teacherIntroduction\":\"没有\",\n" +
                "\t\t\t\"teacherName\":\"罗老师\",\n" +
                "\t\t\t\"learnId\":1,\n" +
                "\t\t\t\"userId\":1,\n" +
                "\t\t\t\"learnTime\":\"2019-01-23\",\n" +
                "\t\t\t\"lastChapterNumber\":1,\n" +
                "\t\t\t\"lastChapterName\":\"路由匹配原理分析与多个路由定义规范\",\n" +
                "\t\t\t\"orderState\":0\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\"courseId\":1,\n" +
                "\t\t\t\"courseName\":\"ASP.NET-MVC网站开发【实战与技能详解】\",\n" +
                "\t\t\t\"courseImage\":\"https://10.url.cn/qqcourse_logo_ng/ajNVdqHZLLC08B9HDxI6BrAyznCvqVuyYabPqAElDJrJlorKZy8iamBvsbHrJElh8yRvQt6nicaXc/356?tp=webp\",\n" +
                "\t\t\t\"cost\":5,\n" +
                "\t\t\t\"courseIntroduction\":\"没有\",\n" +
                "\t\t\t\"labelFirst\":\"后端开发\",\n" +
                "\t\t\t\"labelSecond\":\"网站搭建\",\n" +
                "\t\t\t\"labelThird\":\".net\",\n" +
                "\t\t\t\"courseState\":0, \n" +
                "\t\t\t\"courseTime\":0,\n" +
                "\t\t\t\"learnTotal\":33,\n" +
                "\t\t\t\"teacherId\":1,\n" +
                "\t\t\t\"career\":\"讲师\",\n" +
                "\t\t\t\"teacherIntroduction\":\"没有\",\n" +
                "\t\t\t\"teacherName\":\"罗老师\",\n" +
                "\t\t\t\"learnId\":1,\n" +
                "\t\t\t\"userId\":1,\n" +
                "\t\t\t\"learnTime\":\"2019-01-23\",\n" +
                "\t\t\t\"lastChapterNumber\":1,\n" +
                "\t\t\t\"lastChapterName\":\"路由匹配原理分析与多个路由定义规范\",\n" +
                "\t\t\t\"orderState\":0\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
    }

    @RequestMapping(value = "/api/all/course/selectCourseByCouserId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCourseInfo() {
        return "{\n" +
                "\t\"status\":200,\n" +
                "\t\"message\":\"ok\",\n" +
                "\t\"data\":{\n" +
                "\t\t\"courseId\":1,\n" +
                "\t\t\"courseName\":\"\",\n" +
                "\t\t\"courseImage\":\"\",\n" +
                "\t\t\"cost\":1,\n" +
                "\t\t\"courseIntroduction\":\"courseIntroduction\",\n" +
                "\t\t\"labelFirst\":\"labelFirst\",\n" +
                "\t\t\"labelSecond\":\"labelSecond\",\n" +
                "\t\t\"labelThird\":\"labelThird\",\n" +
                "\t\t\"courseState\":1, \n" +
                "\t\t\"courseTime\":\"courseTime\",\n" +
                "\t\t\"learnTotal\":\"learnTotal\",\n" +
                "\t\t\"teacherId\":1,\n" +
                "\t\t\"career\":\"career\",\n" +
                "\t\t\"teacherIntroduction\":\"teacherIntroduction\",\n" +
                "\t\t\"teacherName\":\"teacherName\",\n" +
                "\t\t\"learnId\":\"learnId\",\n" +
                "\t\t\"userId\":\"userId\",\n" +
                "\t\t\"learnTime\":\"2019-01-22\",\n" +
                "\t\t\"lastChapterNumber\":2,\n" +
                "\t\t\"orderState\":\"orderState\",\n" +
                "\t\t\"chapters\":[{\n" +
                "\t\t\t\"chapterId\":1,\n" +
                "\t\t\t\"chapterName\":\"第一章\",\n" +
                "\t\t\t\"chapterNumber\":1,\n" +
                "\t\t\t\"chapterIntroduction\":\"chapterIntroduction\",\n" +
                "\t\t\t\"chapterUrl\":\"chapterUrl\",\n" +
                "\t\t\t\"chapterTime\":\"3:22\",\n" +
                "\t\t\t\"chapterLength\":\"chapterLength\",\n" +
                "\t\t\t\"chapterState\":\"chapterState\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"chapterId\":2,\n" +
                "\t\t\t\"chapterName\":\"第二章\",\n" +
                "\t\t\t\"chapterNumber\":2,\n" +
                "\t\t\t\"chapterIntroduction\":\"chapterIntroduction\",\n" +
                "\t\t\t\"chapterUrl\":\"chapterUrl\",\n" +
                "\t\t\t\"chapterTime\":\"chapterTime\",\n" +
                "\t\t\t\"chapterLength\":\"chapterLength\",\n" +
                "\t\t\t\"chapterState\":\"chapterState\"\n" +
                "\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
    }

    @RequestMapping(value = "/api/teacher/course/selectCourseByUserId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String selectCourseByUserId() {
        return "{\n" +
                "\t\"status\":200,\n" +
                "\t\"message\":\"ok\",\n" +
                "\t\"data\":{\n" +
                "\t\t\"total\":4,\n" +
                "\t\t\"courses\":[{\n" +
                "\t\t\t\"courseId\":1,\n" +
                "\t\t\t\"courseName\":\"课程1\",\n" +
                "\t\t\t\"courseImage\":\"https://10.url.cn/qqcourse_logo_ng/ajNVdqHZLLC08B9HDxI6BrAyznCvqVuyYabPqAElDJrJlorKZy8iamBvsbHrJElh8yRvQt6nicaXc/356?tp=webp\",\n" +
                "\t\t\t\"cost\":998,\n" +
                "\t\t\t\"courseIntroduction\":\"courseIntroduction\",\n" +
                "\t\t\t\"labelFirst\":\"后端开发\",\n" +
                "\t\t\t\"labelSecond\":\"网站搭建\",\n" +
                "\t\t\t\"labelThird\":\".net\",\n" +
                "\t\t\t\"courseTime\":\"2019-01-01\",\n" +
                "\t\t\t\"learnTotal\":99,\n" +
                "\t\t\t\"commentTotal\":123,\n" +
                "\t\t\t\"courseState\":0\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"courseId\":1,\n" +
                "\t\t\t\"courseName\":\"课程1\",\n" +
                "\t\t\t\"courseImage\":\"https://10.url.cn/qqcourse_logo_ng/ajNVdqHZLLC08B9HDxI6BrAyznCvqVuyYabPqAElDJrJlorKZy8iamBvsbHrJElh8yRvQt6nicaXc/356?tp=webp\",\n" +
                "\t\t\t\"cost\":998,\n" +
                "\t\t\t\"courseIntroduction\":\"courseIntroduction\",\n" +
                "\t\t\t\"labelFirst\":\"后端开发\",\n" +
                "\t\t\t\"labelSecond\":\"网站搭建\",\n" +
                "\t\t\t\"labelThird\":\".net\",\n" +
                "\t\t\t\"courseTime\":\"2019-01-01\",\n" +
                "\t\t\t\"learnTotal\":99,\n" +
                "\t\t\t\"commentTotal\":123,\n" +
                "\t\t\t\"courseState\":1\n" +
                "\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
    }

    @RequestMapping(value = "/api/teacher/course/selectCourseByCourseId", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String selectCourseByCourseId() {
        return "{\n" +
                "\t\"message\":\"您已被封禁讲师权限,剩余时间：2小时\",\n" +
                "\t\"status\":401\n" +
                "}";
    }


//    @RequestMapping(value = "/user/user/uploadUserImage",method = RequestMethod.POST)
//    @ResponseBody
//    public String updateUserImage(@RequestParam String token, @RequestParam(value = "userImageFile", required = false) MultipartFile imageFile, HttpServletRequest request) throws IOException {
//        System.out.println(token);
//        return "123";
//    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(HttpServletRequest request) throws IOException {
//        System.out.println(token);
        return "123";
    }

//    @RequestMapping(value = "/api/teacher/course/selectCourseByCourseId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String selectTeacherCourseByCourseId() {
//
//        return "";
//    }


//    @RequestMapping(value = "user/getUser", method = RequestMethod.POST)
//    public UserModel test() {
//        UserModel userModel = new UserModel();
//        userModel.setName("张三");
//        userModel.setAge(13);
//        return userModel;
//    }
}
