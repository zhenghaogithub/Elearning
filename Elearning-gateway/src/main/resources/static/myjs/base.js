$(document).ready(function () {
    const user_flex = $("#user_flex");//隐藏的悬浮窗
    const head_div = $("#head_div");//用户头像图片div
    const user_head = $("#user_head");//用户头像图片


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
    //更新用户悬浮窗
    updateUserFlex();


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
});


//动态渲染头像悬浮信息
window.updateUserFlex = function () {
    send_ajax({
        type: "POST",
        url: "/api/all/user/getUserCurrent",
        success: function (data) {
            if (data.status === 200) {
                $(".user_name_all").text(data.data.username);
                $(".user_phone_all").text(data.data.phone);
                //这需要后端给出具体的url格式后修改
                $(".user_head_img").attr("src", "http://" + window.location.host + "/" + data.data.userImage);
            } else {
                fail_toast(data.message);
            }
        },
        error: function () {
            fail_toast("获取用户信息失败!");
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
