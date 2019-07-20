$(document).ready(function () {
    // sessionStorage.getItem("testKey");
    // sessionStorage.setItem("testKey","2333");

    refreshByIdentifier(); //先查看是否有定位符 有则直接跳转
    //地址栏定位符改变事件
    window.onhashchange = function (e) {
        refreshByIdentifier();
    };
    //更新个人信息
    updateAllUserInfo();
    //绑定教师信息 如果是的话
    updateTeacherInfo();
    //绑定标签监听
    addLabelListener($("#course_info_edit_div"));
    updateLabel($("#course_info_edit_div"));
    //侧边栏动画
    $("#user_slider_div .active").css({  //给当前active的侧边栏按钮添加样式
        backgroundColor: "dodgerblue",
        color: "white",
    });
    $(".user_slider_btn").mouseenter(function () {
        $(this).stop(true, false).animate({
            backgroundColor: $(this).hasClass("active") ? "#1c72e2" : "#c7c9cb",
            color: "white",
        }, 250);
    }).mouseleave(function () {
        $(this).stop(true, false).animate({
            backgroundColor: $(this).hasClass("active") ? "#1E90FF" : "#eceef0",
            color: $(this).hasClass("active") ? "white" : "black",
        }, 250);
    });
    //侧边栏滚动跟随
    window.slider_height = $("#user_slider_div").offset().top;
    // alert($("#user_slider_div").offset().top);
    // $(".navbar-toggler").on("click", function () {
    //     alert($("#user_slider_div").offset().top);
    //     window.slider_height = $("#user_slider_div").offset().top;
    // });
    $(window.document).scroll(function () {
        // console.log($("#user_slider_div").offset().top - $(window).scrollTop());
        const div = $("#user_slider_div");
        // let dist = window.slider_height - $(window).scrollTop();
        if (div.css("position") == "fixed") {
            let dist = window.slider_height - $(window).scrollTop();
            if (dist >= 0) {
                div.css("position", "absolute");
                div.css("top", "");
            }
        } else {
            let dist = div.offset().top - $(window).scrollTop();
            window.slider_height = div.offset().top;
            if (dist < 0) {
                div.css("position", "fixed");
                div.css("top", "0");
            }
        }
        // var scrolltop = $(document).scrollTop();
        // var top=oldSite.top+scrolltop;
        // $("#floatRight").offset({ top: top });
    });

    //申请按钮
    $("#teacher_apply_btn").mouseenter(function () {
        $(this).stop(true, false).animate({
            backgroundColor: "#32BD32",
        }, 250);
    }).mouseleave(function () {
        $(this).stop(true, false).animate({
            backgroundColor: "#32CD32"
        }, 250);
    });
    //申请按钮事件
    $("#send_teacher_apply_btn").on("click", function () {
        send_ajax_quick({
            type: "POST",
            url: "/teachers/curTeacher",
            option: "申请请求发送",
            data: $.bindModelReverse($("#teacher_info_apply_div")),
            success: function () {
                success_toast("发送请求成功!");
                $("#apply_teacher_info_modal").modal("hide");
                window.location.hash = "#/user/courses_published";
                refreshByIdentifier();
                updateAllUserInfo();
                updateTeacherInfo();
            },
        })
    });
    //发布课程事件
    $("#confirm_publish_course_btn").on("click", function () {
        send_ajax_quick_quick("teachers/curTeacher/course", "发布课程",
            $("#course_info_edit_div"), $("#edit_course_info_modal"), function () {
                refresh_courses_published(1);
            }, {
                cost: $("#course_price_label").val() * 100
            });
    });
    //修改教师信息
    $("#save_teacher_info").on("click", function () {
        send_ajax_quick_quick("/teachers/curTeacher", "修改讲师信息",
            $("#teacher_info_edit_div"), $("#edit_teacher_info_modal"), function () {
                updateTeacherInfo();
            }, null, "PUT");
    });

    //个人信息编辑按钮
    $(".edit_btn_div").mouseenter(function () {
        $(this).find("*").stop(true, false).animate({
            color: "#2271f7",
        }, 100);
    }).mouseleave(function () {
        $(this).find("*").stop(true, false).animate({
            color: "white",
        }, 100);
    });
    //个人信息保存按钮
    $("#save_user_info_btn").on("click", function () {
        send_ajax_quick({
            type: "PUT",
            url: "/user/curuser",
            option: "修改信息",
            data: $.bindModelReverse($("#user_info_edit_div")),
            success: function () {
                success_toast("保存成功!");
                updateAllUserInfo();
                $("#edit_user_info_modal").modal("hide");
            }
        });
    });


    //头像上传功能
    //按钮动画
    $("#upload_head_btn").mouseenter(function () {
        $("#upload_head_btn").stop(true, false).animate({
            color: "white",
            backgroundColor: "#007bff",
        }, 100);
    }).mouseleave(function () {
        $("#upload_head_btn").stop(true, false).animate({
            color: "#007bff",
            backgroundColor: "white",
        }, 100);
    });
    //头像上传
    $("#id_avatar").change(function () {
        // 1. 创建一个读取文件的对象
        let fileReader = new FileReader();
        // 取到当前选中的头像文件
        console.log(this.files[0]);
        // 读取你选中的那个文件
        fileReader.readAsDataURL(this.files[0]);  // 读取文件是需要时间的
        fileReader.onload = function () {
            // 2. 等上一步读完文件之后才 把图片加载到img标签中
            $("#preview_head_img").attr("src", fileReader.result);
        };
    });
    //
    $("#apply_head_btn").click(function () {
        $(this).text("上传中...").prepend(" <span id=\"head_spinner\" class=\"spinner-border spinner-border-sm\"\n" +
            "                                                      role=\"status\"\n" +
            "                                                      aria-hidden=\"true\"></span>");
        uploadHead();
    });
    //修改密码事件
    $("#apply_password_btn").on("click", function () {
        send_ajax_quick({
            type: "PUT",
            url: "/user/curuser/password",
            option: "修改密码",
            data: {
                oldPassword: $("#old_password").val(),
                password: $("#new_password").val()
            },
            success: function () {
                success_toast("修改密码成功!");
                $("#edit_user_password_modal").modal("hide");
            }
        })
    });

    //获取验证码事件
    $("#get_verification_btn").on("click", function () {
        const new_phone_num = $("#new_phone_num");
        if (new_phone_num.val() == null || new_phone_num.val() == "") {
            fail_toast("请输入手机号!");
            return;
        }
        send_ajax_quick({
            url: "api/all/user/sendVerification",
            data: {
                phone: new_phone_num.val()
            },
            option: "获取验证码",
            success: function (data) {
                let time = 60;
                const btn = $("#get_verification_btn");
                btn.attr("disabled", "disabled");
                var t = setInterval(function () {
                    time--;
                    btn.text("等待(" + time + "s)");
                    if (time == 0) {
                        clearInterval(t);
                        btn.text("重新获取");
                        btn.removeAttr("disabled");
                    }
                }, 1000)
            }
        })
    });
    //保存手机号按钮事件
    $("#save_user_phone_btn").on("click", function () {
        const phoneNum = $("#new_phone_num").val();
        const verification = $("#verification_num").val();
        if (isStringBlank(phoneNum) || isStringBlank(verification)) {
            fail_toast("请输入正确的参数");
        }
        if (verification.length != 6) fail_toast("验证码格式错误");
        send_ajax_quick({
            url: "api/user/user/updatePhone",
            option: "绑定手机",
            data: {
                phone: phoneNum,
                verification: verification
            },
            success: function () {
                success_toast("绑定手机成功");
                $("#bind_phone_modal").modal("hide");
                updateAllUserInfo();
            }
        })
    });


    //刷新教师信息
    function updateTeacherInfo() {
        send_ajax_quick({
            type: "GET",
            url: "/teachers/curTeacher",
            option: "获取教师信息",
            success: function (data) {
                $.bindModel($("#teacher_info_edit_div"), data);
            }
        });
    };

    //上传头像文件
    function uploadHead() {
        let file = $('#id_avatar')[0].files[0];
        uploadFile('/user/curuser/image', file, 'userImageFile', 5242880, {},
            function (data) {
                if (data.status == 200) {
                    success_toast("上传成功!");
                    updateAllUserInfo();
                } else {
                    fail_toast("上传失败!");
                }
                $("#head_spinner").remove();
                $("#apply_head_btn").text("应用");
            },
            function () {
                fail_toast("上传失败!");
                $("#head_spinner").remove();
                $("#apply_head_btn").text("应用");
            }
        );
        return true;
    }
});

//根据标签刷新页面
function refreshByIdentifier() {
    let pageId = getLastResource(window.location.hash);
    if (pageId != null) {
        $(".tabs_content").removeClass("active"); //移除当前选项卡
        $("#" + pageId + "_page").addClass("active"); //显示目标
        //标签切换动画
//         #user_slider_div .active {*/
// /*    background-color: dodgerblue;*/
// /*    color: white;*/
// /*}
        if ($("#user_slider_div .active").attr("id").indexOf(pageId) < 0) {  //跳转的不是当前选项卡
            $("#user_slider_div .active").stop(true, false).animate({
                backgroundColor: "#eceef0",
                color: "black",
            }, 250).removeClass("active"); //移除当前标签激活状态
            $("#" + pageId + "_btn").stop(true, false).animate({
                backgroundColor: "#1E90FF",
                color: "white",
            }, 250).addClass("active");  //添加目标标签激活状态
        }
        window["refresh_" + pageId](1); //跳到对应tab的第一页
    }
}

//获取目标页ID
function getLastResource(urlStr) {
    if (!(urlStr.indexOf("#") >= 0)) return null; //没有#号
    const index = urlStr.lastIndexOf("\/");
    let new_urlStr = urlStr.substring(index + 1, urlStr.length);
    return new_urlStr;
}

//更新分页器
function updatePagination(pagination, count, num_per_page, cur_index, updateFun) {
    pagination.empty();
    const num_of_page = Math.ceil(count / num_per_page);
    for (let i = 0; i < num_of_page; i++) {
        pagination.append("<li " + "page_index='" + (i + 1) + "' class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='return false;'>" + (i + 1) + "</a></li>");
    }
    //如果为空，加一页占位
    if (num_of_page == 0) {
        pagination.append("<li " + "page_index='" + (1) + "' class=\"page-item\"><a class=\"page-link\" onclick='return false;'>" + (1) + "</a></li>");
    }
    pagination.prepend("<li " + "page_index='" + 0 + "' class=\"page-item " + (cur_index <= 1 ? "disabled" : "") + "\"><a class=\"page-link\" href=\"#\" onclick='return false;'>上一页</a></li>");
    pagination.append(" <li " + "page_index='" + -1 + "' class=\"page-item " + (cur_index >= num_of_page ? "disabled" : "") + "\"><a class=\"page-link\" href=\"#\" onclick='return false;'>下一页</a></li>");
    $(".page-item").on("click", function () {
        const index = parseInt($(this).attr("page_index"));
        if (index > 0) {
            updateFun(index);
        } else if (index === 0) {
            if (cur_index <= 1) return;
            updateFun(cur_index - 1);
        } else {
            if (cur_index >= num_of_page) return;
            updateFun(cur_index + 1);
        }
    });
    $(".page-item[page_index='" + cur_index + "']").addClass("active");
}

//刷新对应页面的函数
window.num_per_page = 5;

//刷新盒子的通用函数
function refresh_my_box(url, page_num, pageId, path, mapper, error) {
    send_ajax({
        type: "GET",
        url: url,
        data: {
            page: page_num,
            number: num_per_page
        },
        success: function (data) {
            if (data.status === 200) {
                data = data.data;
                updatePagination($("#" + pageId + "_pagination"), data.total, num_per_page, page_num, window["refresh_" + pageId]);
                if (path != null) {
                    for (let node of path) {
                        data = data[node];
                    }
                }
                const box = $("#" + pageId + "_box");
                box.empty();
                for (let i of data) {
                    if (mapper != null)
                        mapper(i);
                    box.append($.bindModel($($[pageId + "_item"]), i));
                }
            } else {
                fail_toast(data.message);
            }
        },
        error: error
    });
};

function refresh_courses_info(page_num) {
    refresh_my_box("user/curuser/learned_course", page_num,
        "courses_info", ["courses"]);
}


function refresh_courses_history(page_num) {

}

function refresh_courses_published(page_num) {
    refresh_my_box("teachers/curTeacher/course", page_num,
        "courses_published", ["courses"], function (data) {
            //价格除以100
            data["cost"] = (data["cost"] / 100).toFixed(1) + "元";
            data["course_url"] = "course_manage.html?courseId=" + data["courseId"];
            // $.mapAndAddProperties(data, { //注意顺序不要换，mapAndAddProperties只会添加，不会其他属性
            //     courseState: {
            //         target: "courseStateColor",
            //         mapper: {
            //             0: "green_font_color",
            //             1: "red_font_color",
            //         }
            //     },
            // });
            // $.mapProperties(data, {
            //     courseState: {
            //         0: "开放中",
            //         1: "审核中",
            //     },
            // });

        },
        function () {
            $("#apply_teacher_info_modal").modal("show");
            window.location.hash = "#/user/user_info";
            refreshByIdentifier();
        });
}

function refresh_order_info(page_num) {
    refresh_my_box("user/curuser/order/orders", page_num,
        "order_info", ["courses"], function (data) {
            data["cost"] = data["cost"].toFixed(1) + "元";
            // data["orderState"] = data["orderState"] === 0 ? "已支付" : "待支付";
            $.mapAndAddProperties(data, { //注意顺序不要换，mapAndAddProperties只会添加，不会其他属性
                orderState: {
                    target: "orderStateColor",
                    mapper: {
                        0: "green_font_color",
                        1: "red_font_color",
                    }
                },
            });
            $.mapProperties(data, {
                orderState: {
                    0: "待支付",
                    1: "已支付",
                },
            });
        });
}

function refresh_user_info() {
    updateAllUserInfo();
}

//刷新所有用户个人信息
function updateAllUserInfo() {
    //更新个人信息背景栏
    send_ajax_quick({
        type: "GET",
        url: "user/curuser",
        option: "获取用户信息",
        success: function (data) {
            $(".user_name_all").text(data.name);
            $(".user_phone_all").text(data.telphone);
            $(".user_head_img").attr("src", data.headUrl);
            $(".user_description_all").text(data.userDescribtion);
            $.bindModel($("#user_info_detail"), data);
            $.bindModel($("#user_info_edit_div"), data);
            // //显示、隐藏申请讲师按钮   power的含义需要与后端确认
            // const powerJson = $.parseJSON(data.power);
            // if (powerJson != null && powerJson.allTeacher == "allow") {
            //     $("#courses_published_btn_li").show();
            //     $("#teacher_apply_btn_li").hide();
            //     $("#user_state_label").val("讲师");
            //     window.isTeacher = true;
            // } else {
            //     $("#courses_published_btn_li").hide();
            //     $("#teacher_apply_btn_li").show();
            //     $("#user_state_label").val("普通用户");
            //     window.isTeacher = false;
            // }
        }
    });
};


//课程详细跳转按钮
function goto_course_info(that) {
    // window.location.href = "course_manage.html?courseId=" + $(that).parent().attr("courseId");
}

//删除学习课程按钮事件
function delete_learned_course_btn_click(that) {
    const modal = $("#delete_learned_course_modal");
    modal.attr("courseId", $(that).attr("courseId"));
    modal.modal("show");
}

function confirm_delete_learned_course_btn_click(that) {
    send_ajax({
        type: "POST",
        url: "api/teacher/course/deleteCourse",
        data: {
            courseId: $(that).attr("courseId")
        },
        success: function (data) {
            if (data.status == 200) {
                success_toast("删除成功!");
                $("#delete_learned_course_modal").modal("hide");
            } else {
                fail_toast("删除课程失败!原因是:" + data.message);
            }
        },
        error: function () {
            fail_toast("下架课程失败!发生未知错误");
        }
    });
}

//删除发布的课程按钮事件
function delete_published_course_btn_click(that) {
    const modal = $("#delete_published_course_modal");
    $("#delete_published_course_btn").attr("courseId", $(that).attr("courseId"));
    modal.modal("show");
}

function confirm_delete_published_course_btn_click(that) {
    send_ajax({
        type: "DELETE",
        url: "teachers/curTeacher/course/{courseId}",
        data: {
            courseId: $(that).attr("courseId")
        },
        success: function (data) {
            if (data.status == 200) {
                success_toast("下架成功!");
                $("#delete_published_course_modal").modal("hide");
                refresh_courses_published(1);
            } else {
                fail_toast("下架课程失败!原因是:" + data.message);
            }
        },
        error: function (data) {
            fail_toast("下架课程失败!发生未知错误");
        }
    });
}

//继续学习按钮
function continue_learn_btn_click(that) {
    const chapterId = $(that).attr("lastChapterId");
    if (chapterId != null) {
        localStorage.setItem("chapterId", $(that).attr("lastChapterId"));
        window.location.href = "watchVideo.html";
    } else {
        window.location.href = "CourseInfo.html?" + "courseId=" + $(that).parent().attr("courseId");
    }

}