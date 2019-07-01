$(document).ready(function () {
    //为章节div添加点击事件
    //测试方法
    function addChapterDivClickEvent(index, element) {
        $(element).on("click", function () {
            const modal = $("#edit_chapter_modal");
            modal.attr("chapter_id", $(this).find("div[chapter_id]").attr("chapter_id")); //在模态框上记录点击的章节ID
            modal.modal("show");  //显示模态框

            //把章节数据填入表格  需要ajax 以后写
            $("#chapter_name_label_edit").val($(this).find(".chapter_name").text().trim());
            //...

        })
    };
    //更新课程信息
    const courseId = getUrlParam("courseId");
    if (courseId != false) {
        send_ajax({
            type: "POST",
            data: {
                courseId: courseId
            },
            url: "/api/teacher/course/selectCourseByCourseId",
            success: function (data) {
                if (data.status === 200) {
                    data = data.data;
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
                    $.bindModel($("#course_info_edit_div"), data);
                    data["cost"] = data["cost"].toFixed(1) + "元";
                    $.bindModel($("#course_model_div"), data);

                    $(".swiper-wrapper").empty();
                    for (let chapter of data.chapters) {
                        chapter.chapterNumber = "第" + chapter.chapterNumber + "章";
                        $.mapAndAddProperties(chapter, { //注意顺序不要换，mapAndAddProperties只会添加，不会其他属性
                            chapterState: {
                                target: "chapterStateColor",
                                mapper: {
                                    0: "green_font_color",
                                    1: "red_font_color",
                                }
                            },
                        });
                        $.mapProperties(chapter, {
                            chapterState: {
                                0: "开放中",
                                1: "审核中",
                            },
                        });
                        $(".swiper-wrapper").append($.bindModel($($["chapter_info_item"]), chapter));
                    }
                    $(".swiper-slide").each(addChapterDivClickEvent);
                } else {
                    fail_toast(data.message);
                }
            },
            error: function () {
                fail_toast("获取用户信息失败!");
            },
        });
    } else {
        fail_toast("获取课程ID失败");
    }

    function getUrlParam(param) {
        const query = window.location.search.substring(1);
        const vars = query.split("&");
        for (let i = 0; i < vars.length; i++) {
            const pair = vars[i].split("=");
            if (pair[0] == param) {
                return pair[1];
            }
        }
        return (false);
    }

    var swiper = new Swiper('.swiper-container', {
        slidesPerView: 5,
        spaceBetween: 15,
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        observer: true,
        observeParents: true,
        // breakpoints: {
        //     1024: {
        //         slidesPerView: 4,
        //         spaceBetween: 40,
        //     },
        //     768: {
        //         slidesPerView: 3,
        //         spaceBetween: 30,
        //     },
        //     640: {
        //         slidesPerView: 2,
        //         spaceBetween: 20,
        //     },
        //     320: {
        //         slidesPerView: 1,
        //         spaceBetween: 10,
        //     }
        // }
    });
});

