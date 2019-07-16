$(document).ready(function () {
    //绑定标签监听
    addLabelListener($("#course_info_edit_div"));
    //更新课程信息
    updateCourseInfo();
    //初始化swiper
    updateSwiper();

    // alert(sessionStorage.getItem("testKey"));

    $("#course_img_div").mouseenter(function () {
        $("#modify_course_img_div_flex").stop(true, false).fadeIn();
    }).mouseleave(function () {
        $("#modify_course_img_div_flex").stop(true, false).fadeOut();
    });

    //封面上传监听
    $("#course_img_edit").change(function () {
        if (this.files[0] == null)
            return;
        let fileReader = new FileReader();
        fileReader.readAsDataURL(this.files[0]);
        fileReader.onload = function () {
            $("#course_img_ediv").attr("src", fileReader.result);
        };
    });
    //视频上传监听
    $("#course_video_reload").change(function () {
        if (this.files[0] == null)
            return;
        const file = this.files[0];
        // let fileReader = new FileReader();
        // fileReader.readAsDataURL(file);
        $("#video_name").text("文件名: " + file.name);
        $("#reupload_file_size").text((file.size / 1048576).toFixed(1) + " M");
        // fileReader.onload = function () {
        //     $("#video_name").text("文件名: " + file.name);
        //     $("#reupload_file_size").text((file.size / 1048576).toFixed(1) + " M");
        // };
    });


    //下架课程按钮
    $("#delete_course_btn").on("click", function () {
        send_ajax_quick_quick("api/teacher/course/deleteCourse", "下架课程",
            {
                courseId: window.courseId
            }, $("#delete_published_course_modal"));
    });
    //删除章节按钮
    $("#confirm_delete_course_btn").on("click", function () {
        send_ajax({
            type: "POST",
            url: "api/teacher/chapter/deleteChapter",
            data: {
                courseId: window.courseId,
                chapterId: $("#edit_chapter_modal").attr("chapter_id"),
            },
            success: function (data) {
                if (data.status == 200) {
                    success_toast("删除成功!");
                    $("#delete_chapter_modal").modal("hide");
                    $("#edit_chapter_modal").modal("hide");
                    updateChapterInfo();
                } else {
                    fail_toast("删除章节失败!原因是:" + data.message);
                }
            },
            error: function () {
                fail_toast("删除章节失败!发生未知错误");
            }
        })
    });

    //保存课程信息按钮
    $("#save_course_info_btn").on("click", function () {
        let data = $.bindModelReverse($("#course_info_edit_div"));
        data["courseId"] = window.courseId;
        data["cost"] = data["cost"] * 100;
        send_ajax({
            type: "POST",
            url: "api/teacher/course/updateCourse",
            data: data,
            success: function (data) {
                if (data.status == 200) {
                    $("#edit_course_info_modal").modal("hide");
                    success_toast("修改成功!");
                    updateCourseInfo();
                } else {
                    fail_toast("修改失败！原因是：" + data.message);
                }
            },
            error: function () {
                fail_toast("修改课程信息失败!发生未知错误");
            }
        });
    });
    //保存章节信息按钮
    $("#save_chapter_btn").on("click", function () {
        //看看有没有视频要传
        const hasVideo = $("#course_video_reload")[0].files.length > 0;
        if (hasVideo) {
            uploadChapterVideo($("#edit_chapter_modal").attr("chapter_id"));
        }
        let data = $.bindModelReverse($("#chapter_info_edit_div"));
        data["chapterId"] = $("#edit_chapter_modal").attr("chapter_id");
        data["courseId"] = window.courseId;
        send_ajax({
            type: "POST",
            url: "api/teacher/chapter/updateChapter",
            data: data,
            success: function (data) {
                if (data.status == 200) {
                    if (!hasVideo) {
                        $("#edit_chapter_modal").modal("hide");
                        success_toast("修改成功!");
                        updateChapterInfo();
                    }
                } else {
                    fail_toast("修改失败！原因是：" + data.message);
                }
            },
            error: function () {
                fail_toast("修改课程信息失败!发生未知错误");
            }
        });
    });
    //添加章节按钮
    $("#add_chapter_btn").on("click", function () {
        send_ajax_quick_quick("api/teacher/chapter/addChapter", "添加章节",
            $("#chapter_info_add_div"), $("#add_chapter_modal"), function () {
                updateChapterInfo();
            }, {courseId: window.courseId});
    });
    //保存封面按钮
    $("#apply_head_btn").on("click", function () {
        addSpinner($(this), "上传中...");
        uploadCourseImg();
    });


    //为章节div添加点击事件
    //测试方法
    function addChapterDivClickEvent(index, element) {
        $(element).on("click", function () {
            const modal = $("#edit_chapter_modal");
            modal.attr("chapter_id", $(this).find("div[chapter_id]").attr("chapter_id")); //在模态框上记录点击的章节ID
            modal.modal("show");  //显示模态框

            $("#chapter_name_label_edit").val($(this).find(".chapter_name").text().trim());
            $("#chapter_num_label_edit").val($(this).find(".course_chapter_num").text().slice(1, -1));
            $("#chapter_introduction_label_edit").val($(this).find(".chapter_introduction").text());
            //...

        })
    };

    function updateCourseInfo() {
        //更新课程信息
        const courseId = getUrlParam("courseId");
        if (courseId != false) {
            window.courseId = courseId;
            send_ajax({
                data: {
                    courseId: courseId
                },
                url: "api/teacher/course/selectCourseByCourseId",
                success: function (data) {
                    if (data.status === 200) {
                        data = data.data;
                        //验证权限
                        validateTeacher(data.teacherId);
                        //除以100
                        data["cost"] = data["cost"] / 100;
                        data.courseImage = data.courseImage;
                        $.bindModel($("#course_info_edit_div"), data);
                        data["cost"] = data["cost"].toFixed(1) + "元";
                        $.bindModel($("#course_model_div"), data);
                        //顺便修改封面修改页面的图片
                        $("#course_img_ediv").attr("src", data.courseImage);
                        //更新章节信息
                        updateChapterInfo();
                        //更新标签数据
                        updateLabel($("#course_info_edit_div"));

                    } else {
                        fail_toast(data.message);
                    }
                },
                error: function () {
                    fail_toast("获取课程信息失败!");
                },
            });
        } else {
            fail_toast("获取课程ID失败");
        }
    }

    function updateChapterInfo() {
        send_ajax_quick({
            url: "api/teacher/chapter/selectRealChapterByCourseId",
            option: "获取章节信息",
            data: {
                courseId: window.courseId,
                page: 1,
                number: 9999
            },
            success: function (data) {
                $(".swiper-wrapper").empty();
                for (let chapter of data.chapters) {
                    chapter.chapterNumber = "第" + chapter.chapterNumber + "章";
                    $.mapAndAddProperties(chapter, { //注意顺序不要换，mapAndAddProperties只会添加，不会其他属性
                        chapterState: {
                            target: "chapterStateColor",
                            mapper: {
                                0: "green_font_color",
                                1: "red_font_color",
                                2: "red_font_color",
                                3: "red_font_color",
                            }
                        },
                    });
                    $.mapProperties(chapter, {
                        chapterState: {
                            0: "已审核",
                            1: "未审核",
                            2: "审核未通过",
                            3: "已删除"
                        },
                    });
                    $(".swiper-wrapper").append($.bindModel($($["chapter_info_item"]), chapter));
                }
                $(".swiper-slide").each(addChapterDivClickEvent);
            }
        });
    }

    //验证是否有该课程的权限
    function validateTeacher(teacherId) {
        send_ajax_quick({
            url: "api/teacher/teacher/selectTeacherByUserId",
            option: "教师权限验证",
            async: false,
            success: function (data) {
                if (data.teacherId != teacherId) {
                    fail_toast("您没有管理该课程的权限!");
                    window.location.href = "userinfo.html";
                    window.hasRights = false;
                }
                window.hasRights = true;
            },
            error: function () {
                window.location.href = "userinfo.html";
                window.hasRights = false;
            }
        });
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

    //上传封面
    function uploadCourseImg() {
        let file = $('#course_img_edit')[0].files[0];
        uploadFile('api/teacher/course/uploadCourseImage', file, 'courseImageFile', 5242880,
            {
                courseId: window.courseId
            },
            function (data) {
                if (data.status == 200) {
                    success_toast("上传成功!");
                    updateCourseInfo();
                } else {
                    fail_toast("上传失败!");
                }
                removeSpinner($("#apply_head_btn"), "应用");
            },
            function () {
                fail_toast("上传失败!");
                removeSpinner($("#apply_head_btn"), "应用");
            }
        );
        return true;
    }

    //上传章节视频
    function uploadChapterVideo(chapterId) {
        addSpinner($("#save_chapter_btn"), "视频上传中...")
        let file = $('#course_video_reload')[0].files[0];
        uploadFile('api/teacher/chapter/uploadChapterVideo', file, 'chapterVideoFile', 2147483648,
            {
                chapterId: chapterId
            },
            function (data) {
                if (data.status == 200) {
                    success_toast("上传成功!");
                    $("#edit_chapter_modal").modal("hide");
                    clearVideoUpload();
                    updateCourseInfo();
                } else {
                    fail_toast("上传失败!");
                }
                removeSpinner($("#save_chapter_btn"), "修改章节");
            },
            function () {
                fail_toast("上传失败!");
                removeSpinner($("#save_chapter_btn"), "修改章节");
            },
            function () {
                let myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', function (e) {
                        if (e.lengthComputable) {
                            let percent = Math.floor(e.loaded / e.total * 100);
                            $("#upload_progress_bar").css("width", (percent) + "%");
                        }
                    }, false);
                }
                return myXhr;
            }
        );
        return true;
    }


    //添加等待小圆圈
    function addSpinner(element, text) {
        element.text(text).prepend(" <span class=\"spinner-border spinner-border-sm\"\n" +
            "                                                      role=\"status\"\n" +
            "                                                      aria-hidden=\"true\"></span>");
        element.attr("disabled", "disabled");
    }

    //移出等待小圆圈
    function removeSpinner(element, text) {
        $(".spinner-border").remove();
        element.text(text);
        element.removeAttr("disabled");
    }

    //清空视频栏
    function clearVideoUpload() {
        $("#course_video_reload").val('');
        $("#video_name").text("未选择文件");
        $("#reupload_file_size").text("0 M");
        $("#upload_progress_bar").css("width", "0%");
    }

    function updateSwiper() {
        window.swiper = new Swiper('.swiper-container', {
            slidesPerView: 5,
            spaceBetween: 15,
            initialSlide: 0,
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
            observer: true,
            observeParents: true,
            breakpoints: {
                //     //     1024: {
                //     //         slidesPerView: 4,
                //     //         spaceBetween: 40,
                //     //     },
                //     //     768: {
                //     //         slidesPerView: 3,
                //     //         spaceBetween: 30,
                //     //     },
                //     //     640: {
                //     //         slidesPerView: 2,
                //     //         spaceBetween: 20,
                //     //     },
                400: {
                    slidesPerView: 2,
                    spaceBetween: 10,
                    initialSlide: 0
                }
            }
        });
    }
});

