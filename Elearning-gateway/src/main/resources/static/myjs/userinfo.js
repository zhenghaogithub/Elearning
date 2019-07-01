$(document).ready(function () {
    refreshByIdentifier(); //先查看是否有定位符 有则直接跳转
    //地址栏定位符改变事件
    window.onhashchange = function (e) {
        refreshByIdentifier();
    };
    //更新个人信息背景栏
    send_ajax({
        type: "POST",
        url: "/api/all/user/getUserCurrent",
        success: function (data) {
            if (data.status === 200) {
                // $(".user_name_all").text(data.data.username);
                // $(".user_phone_all").text(data.data.phone);
                // //这需要后端给出具体的url格式后修改
                // $(".user_head_img").attr("src", "http://" + window.location.host + "/" + data.data.userImage);
                $(".user_description_all").text(data.data.userIntroduction);
                //显示、隐藏申请讲师按钮   power的含义需要与后端确认
                if (data.data.power === 2) {
                    $("#courses_published_btn_li").show();
                    $("#teacher_apply_btn_li").hide();
                } else {
                    $("#courses_published_btn_li").hide();
                    $("#teacher_apply_btn_li").show();
                }
            } else {
                fail_toast(data.message);
            }
        },
        error: function () {
            fail_toast("获取用户信息失败!");
        },
    });
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
        if (!uploadHead()) {
            $("head_spinner").remove();
            $(this).text("应用");
        }
    });
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
function refresh_my_box(url, page_num, pageId, path, mapper) {
    send_ajax({
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
    })
};

function refresh_courses_info(page_num) {
    refresh_my_box("/api/all/course/selectCourseLearned", page_num,
        "courses_info", ["courses"]);
}


function refresh_courses_history(page_num) {

}

function refresh_courses_published(page_num) {
    refresh_my_box("/api/teacher/course/selectCourseByUserId", page_num,
        "courses_published", ["courses"], function (data) {
            data["cost"] = data["cost"].toFixed(1) + "元";
            data["course_url"] = "course_manage.html?courseId=" + data["courseId"];
            $.mapAndAddProperties(data, { //注意顺序不要换，mapAndAddProperties只会添加，不会其他属性
                courseState: {
                    target: "courseStateColor",
                    mapper: {
                        0: "green_font_color",
                        1: "red_font_color",
                    }
                },
            });
            $.mapProperties(data, {
                courseState: {
                    0: "开放中",
                    1: "审核中",
                },
            });
        });
}

function refresh_order_info(page_num) {
    refresh_my_box("/api/all/course/selectCourseLearned", page_num,
        "order_info", ["courses"], function (data) {
            data["cost"] = data["cost"].toFixed(1) + "元";
            data["orderState"] = data["orderState"] === 0 ? "已支付" : "待支付";
        });
}

function refresh_user_info() {
    send_ajax({
        type: "POST",
        url: "/api/all/user/getUserCurrent",
        success: function (data) {
            if (data.status === 200) {
                $(".user_name_all").text(data.data.username);
                $(".user_phone_all").text(data.data.phone);
                $(".user_head_img").attr("src", "http://" + window.location.host + "/" + data.data.userImage);
                //绑定其他个人信息
                $.bindModel($("#user_info_detail"), data.data);
                $.bindModel($("#user_info_edit_div"), data.data);
            } else {
                fail_toast(data.message);
            }
        },
        error: function () {
            fail_toast("获取用户信息失败!");
        },
    });
}


//上传头像文件
function uploadHead() {
    let file = $('#id_avatar')[0].files[0];
    if (file == null) {
        fail_toast("未选择任何图片!")
        return false;
    }
    if (file.size > 5242880) {
        fail_toast("请选择小于5M的图片!")
        return false;
    }
    let formData = new FormData();
    formData.append('file', file);
    // formData.append('sizeid', 123);
    $.ajax({
        url: '???',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            $("head_spinner").remove();
            $("#apply_head_btn").text("应用");
            success_toast("上传成功!")
        },
        error: function (data) {
            fail_toast("上传失败!");
        }
    });
    return true;
}

//课程详细跳转按钮
function goto_course_info(that) {
    // window.location.href = "course_manage.html?courseId=" + $(that).parent().attr("courseId");
}