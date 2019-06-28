$(document).ready(function () {
    const user_flex = $("#user_flex");//隐藏的悬浮窗
    const head_div = $("#head_div");//用户头像图片div
    const user_head = $("#user_head");//用户头像图片

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
});
