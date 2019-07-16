$(document).ready(function () {
    const user_flex = $("#user_flex");//隐藏的悬浮窗
    const head_div = $("#head_div");//用户头像图片div
    const user_head = $("#user_head");//用户头像图片
    window.debug = true;

    if (localStorage.getItem("token") == null) {
        window.location.href = "login_page.html";
    } else {
        window.token = localStorage.getItem("token");
    }

    // //获取用户token  测试用
    // send_ajax({
    //     type: "POST",
    //     url: "api/all/user/login",
    //     data: {
    //         username: "user",
    //         password: "123456"
    //     },
    //     async: false,
    //     success: function (data) {
    //         if (data.status === 200) {
    //             window.token = data.data.token;
    //         } else {
    //             fail_toast(data.message);
    //         }
    //     },
    //     error: function () {
    //         fail_toast("获取用户信息失败!");
    //     },
    // });

    //更新用户悬浮窗
    updateUserFlex();
    //设置定期获取消息数量的监听器 暂时只获取一次
    updateMessageCount();

    //自定义按钮悬浮
    $(".my_button").attr("old_color", function () {
        return $(this).css("color");        //通过回调函数为每个按钮添加属性记录其原本的颜色
    }).mouseenter(function () {
        $(this).stop(true, false).animate({
            color: "white",
            backgroundColor: $(this).attr("old_color"),
        }, 100);
    }).mouseleave(function () {
        $(this).stop(true, false).animate({
            color: $(this).attr("old_color"),
            backgroundColor: "white",
        }, 100);
    });

    //用户头像悬浮阴影
    user_head.mouseenter(function () {
        // $(this).css({
        //     borderColor: "#2270ef",
        // });
        $(this).animate({
            borderColor: "#2270ef",
        })
    }).mouseleave(function () {
        // $(this).css({
        //     borderColor: "#343a40",
        // });
        $(this).animate({
            borderColor: "#343a40",
        })
    });
    //消息提醒悬浮变色
    $("#alarm_ico_div").mouseenter(function () {
        $("#alarm_ico_word").stop(true, false).animate({
            color: "#2270ef",
        }, 300);
    }).mouseleave(function () {
        $("#alarm_ico_word").stop(true, false).animate({
            color: "#b9bbbe",
        }, 300);
    });
    //用户菜单悬浮窗
    user_head.mouseenter(function () {  //进入头像区域显示悬浮窗
        //由于不需要移动悬浮窗 这段不需要了
        // user_flex.css({
        //     right: "3px",
        //     top: $(this).offset().top + $(this).height() - 2 + "px",  //减去一个值使悬浮窗和头像的区域交界没有缝隙
        // });
        user_flex.stop(true, false).fadeIn("fast");
    });
    head_div.mouseleave(function (e) {  //离开整个头像区域才判断是否要消失
        if (!(isInsideBox(head_div, e) || isInsideBox(user_flex, e))) {  //如果离开，则信息窗口消失
            user_flex.fadeOut("fast");
        }
    });
    user_flex.mouseleave(function (e) {  //离开悬浮窗时判断
        if (!(isInsideBox(head_div, e) || isInsideBox(user_flex, e))) {     //如果离开，则信息窗口消失
            user_flex.fadeOut("fast");
        }
    });


    //判断鼠标是否移出判定区(判定区为用户头像区域加上悬浮窗区域)
    function isInsideBox(element, e) {
        const x = e.clientX;
        const y = e.clientY;
        const x1 = element.offset().left;
        const y1 = element.offset().top;
        const x2 = x1 + element.width();
        const y2 = y1 + element.height(); //根据4个点判断鼠标是否移出装备box框
        return !(x <= x1 || x >= x2 || y <= y1 || y >= y2);
    }

    //获取消息数量并更新监听器
    function updateMessageCount() {
        send_ajax_quick({
            url: "api/user/message/selectTotalUnreadMessage",
            option: "获取消息数量",
            success: function (data) {
                if (data.total <= 0) {
                    $("#alarm_ico_count").text(data.total);
                    $("#alarm_ico_count").hide();
                } else if (data.total > 0 && data.total < 100) {
                    $("#alarm_ico_count").text(data.total);
                    $("#alarm_ico_count").show();
                } else {
                    $("#alarm_ico_count").text(99);
                    $("#alarm_ico_count").hide();
                }
            }
        })
    }
});


//动态渲染头像悬浮信息
window.updateUserFlex = function () {
    send_ajax({
        type: "POST",
        url: "api/user/user/getUserCurrent",
        success: function (data) {
            if (data.status === 200) {
                $(".user_name_all").text(data.data.username);
                $(".user_phone_all").text(data.data.phone);
                //这需要后端给出具体的url格式后修改
                if (window.debug == false) {  //由于可能部署在不同域名下 需要变为绝对路径
                    $(".user_head_img").attr("src", data.data.userImage);
                } else {
                    $(".user_head_img").attr("src", "http://120.55.165.31:8080/Elearning/" + data.data.userImage);
                }
                window.userId = data.data.userId;
                const powerJson = $.parseJSON(data.data.power);
                if (powerJson != null && powerJson.allTeacher == "allow") {
                    window.isTeacher = true;
                    $("#open_live_btn").off("click").on("click", function () {
                        window.open("http://182.92.1.116:3000/livecreator?t=" + window.token);
                    });
                } else {
                    window.isTeacher = false;
                    $("#open_live_btn").off("click").on("click", function () {
                        fail_toast("您还不是讲师，无法开启直播");
                    });
                }
            } else {
                fail_toast(data.message);
                //如果登录失效 返回登录页面
                window.location.href = "login_page.html";
            }
        },
        error: function () {
            fail_toast("获取用户信息失败!");
            //如果登录失效 返回登录页面
            window.location.href = "login_page.html";
        },
    });
};


//弹出提示框函数
window.toast = function (message, style, time) {
    style = (style === undefined) ? 'alert-success' : style;
    time = (time === undefined) ? 1200 : time;
    $(".alert").remove();  //清空先前的提示框
    $('<div>')
        .appendTo('body')
        .addClass('alert ' + style)
        .html(message)
        .fadeIn()
        .delay(time)
        .fadeOut();
};

// 成功提示
window.success_toast = function (message, time) {
    toast(message, 'alert-success', time);
};

// 失败提示
window.fail_toast = function (message, time) {
    toast(message, 'alert-danger', time);
};

// 警告
window.warning_toast = function (message, time) {
    toast(message, 'alert-warning', time);
};

// 信息提示
window.info_toast = function (message, time) {
    toast(message, 'alert-info', time);
};

window.label_map = new Map();
label_map.set("前端开发", ["HTML/CSS", "JavaScript", "Vue.js", "React.JS", "Angular", "Node.js", "jQuery", "Bootstrap", "其它"]);
label_map.set("后端开发", ["Java", "SpringBoot", "Spring Cloud", "Python", "Go", "C", "C++", "C#", "其它"]);
label_map.set("前沿技术", ["微服务", "区块链", "机器学习", "深度学习", "计算机视觉", "自然语言处理", "数据分析&挖掘", "其他"]);
label_map.set("数据库", ["MySQL", "Redis", "MangoDB", "Oracle", "SQL Sever", "NoSql", "其他"]);
label_map.set("云计算&大数据", ["大数据", "Hadoop", "Spark", "Hbase", "Storm", "云计算", "Docker", "其他"]);
label_map.set("运维&测试", ["运维", "中间件", "运维工具", "Linux", "功能测试", "性能测试", "安全测试", "其他"]);

function addLabelListener(div) {
    // div.children("select:first").each(updateLabel);
    div.children("select:first").on("change", function () {
        updateLabel(div);
    });
}

function updateLabel(div) {
    const first_label = div.children("select:eq(0)");
    if (first_label.val() != null) {
        const second_label = div.children("select:eq(1)");
        second_label.empty();
        for (let label of window.label_map.get(first_label.val())) {
            second_label.append("<option value='" + label + "'>" + label + "</option>");
        }
    }
}

function isStringBlank(str) {
    return str == null || str == "" || str.trim() == "";
}

//搜索
function setSearchKey() {
    let keyWord = $("input[name='search-box']").val();
    localStorage.setItem("search-key", keyWord);
}

//退出登录
function logout() {
    send_ajax_quick({
        url: "api/user/user/logout",
        option: "退出登录",
        success: function (data) {
            window.location.href = "login_page.html";
        }
    })
}