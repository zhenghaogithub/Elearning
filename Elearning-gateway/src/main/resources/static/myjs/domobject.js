$(document).ready(function () {
    $.extend({
        bindModel: function (view, model) {
            if (view == null || model == null) return;
            let dom = $(view);
            dom.find("*[model]").each(function () {
                if ($(this)[0].hasAttribute("model_target")) {
                    $(this).attr($(this).attr("model_target"), model[$(this).attr("model")]);
                } else {
                    $(this).text(model[$(this).attr("model")]);
                }
            });
            return dom[0];
        },
        courses_info_item: " <div class=\"courses_info_item  d-flex p-0  border-bottom justify-content-around\">\n" +
            "                        <img model='courserImage' model_target='src' class=\"course_img\"\n" +
            "                            >\n" +
            "                        <div class=\"course_info_desc p-3\">\n" +
            "                            <p>\n" +
            "                                <strong model='courseName' class=\"course_name_label\"></strong>\n" +
            "                                <span class=\"course_status\">学习中</span>\n" +
            "                            </p>\n" +
            "                            <p>\n" +
            "<!--                                <span class=\"learn_percent\">进度:23%</span>-->\n" +
            "                                <span class=\"ml-1\">上次学到 : </span>\n" +
            "                                <span model='lastChapterName' class=\"last_chapter_label\" style=\"color: #b9bbbe;\"></span>\n" +
            "                            </p>\n" +
            "                        </div>\n" +
            "                        <p class=\"start_time_label p-2\"><span>添加时间 : </span><span\n" +
            "                                style=\"color:#b9bbbe;\" model='learnTime'></span></p>\n" +
            "<!--                        <p class=\"learn_time_label p-2\"><span>学习总时长 : </span><span style=\"color: #b9bbbe;\">23h</span>-->\n" +
            "<!--                        </p>-->\n" +
            "                        <div class=\"d-flex flex-wrap\" style=\"width: 6rem\">\n" +
            "                            <button id=\"remuse_learn_btn\" class=\"btn btn-primary my-1\">继续学习</button>\n" +
            "                            <button id=\"delete_course_btn\" class=\"btn btn-danger my-1\">删除课程</button>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>",
        courses_published_item: "<div class=\"courses_published_item  d-flex p-0  border-bottom justify-content-around\">\n" +
            "                        <img  model='courserImage' model_target='src' class=\"course_img\"\n" +
            "                             >\n" +
            "                        <div class=\"published_info_desc p-3\">\n" +
            "                            <p class=\"mb-2\">\n" +
            "                                <strong model='courseName'  class=\"course_name_label\"></strong>\n" +
            "                            </p>\n" +
            "                            <div class=\"course_label_div mb-2\">\n" +
            "                                <span model='labelFirst'  class=\"course_label label_first\"></span>\n" +
            "                                <span model='labelSecond'  class=\"course_label label_second\"></span>\n" +
            "                                <span model='labelThird'  class=\"course_label label_third\"></span>\n" +
            "                            </div>\n" +
            "                            <div class=\"d-flex justify-content-between\">\n" +
            "                                <span>课程状态 : <span model='courseState'  class=\"course_state_open\"></span></span>\n" +
            "                                <span>价格 : <span  model='cost'  class=\"course_price\"></span></span>\n" +
            "                                <span>学习人数 : <span model='learnTotal' class=\"learn_count gray_font_color\"></span></span>\n" +
            "                                <span>评论 : <span model='comment_count' class=\"comment_count gray_font_color\"></span></span>\n" +
            "                                <!--                                <span class=\"last_time_label\">3 : 12</span>-->\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <p class=\"start_time_label p-2\"><span>发布时间 : </span><span\n" +
            "                                class=\"gray_font_color\"  model='courseTime' ></span></p>\n" +
            "                        <div model='courseId' model_target='courseId' class=\"d-flex flex-wrap\" style=\"width: 6rem\">\n" +
            "                            <button href='course_manage.html?a=1' id=\"course_info_btn\" onclick='goto_course_info(this)' class=\"btn btn-primary my-1\">课程详细</button>\n" +
            "                            <button id=\"withdraw_course_btn\" class=\"btn btn-danger my-1\">下架课程</button>\n" +
            "                        </div>\n" +
            "                    </div>",
        order_info_item: "<div class=\"order_info_item  d-flex p-0  border-bottom justify-content-around\">\n" +
            "                        <img  model='courserImage' model_target='src' class=\"course_img\"\n" +
            "                            >\n" +
            "                        <div class=\"order_info_desc p-3\">\n" +
            "                            <p>\n" +
            "                                <strong model='courseName'  class=\"course_name_label\"></strong>\n" +
            "                            </p>\n" +
            "                            <div class=\"d-flex justify-content-between\">\n" +
            // "                                <span>订单号 : <span class=\"gray_font_color\">2019061100001</span></span>\n" +
            "                                <span>订单状态 : <span model='orderState'  class=\"course_state_open\">已支付</span></span>\n" +
            "                                <span>价格 : <span model='cost' class=\"course_price\"></span></span>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <p class=\"start_time_label p-2\"><span>下单时间 : </span><span\n" +
            "                                style=\"color:#b9bbbe;\" model='learnTime'></span></p>\n" +
            "                        </p>\n" +
            "                    </div>",
    });
});


// "<div class=\"course_info_item  d-flex p-0  border-bottom justify-content-around\">\n" +
// "<img class=\"course_img\"\n" +
// "src=\"" + i.courserImage + "\">\n" +
// "<div class=\"course_info_desc p-3\">\n" +
// "<p>\n" +
// "<strong class=\"course_name_label\">" + i.courseName + "</strong>\n" +
// "<span class=\"course_status\">学习中</span>\n" +
// "</p>\n" +
// "<p>\n" +
// // "<span class=\"learn_percent\">进度:23%</span>\n" +
// "<span class=\"ml-1\">上次学到 : </span>\n" +
// "<span class=\"last_chapter_label\" style=\"color: #b9bbbe;\">" + data2.data.chapters[i.lastChapterNumber - 1].chapterName + "</span>\n" +
// "</p>\n" +
// "</div>\n" +
// "<p class=\"start_time_label p-2\"><span>添加时间 : </span><span\n" +
// "                                style=\"color:#b9bbbe;\">" + i.learnTime + "</span></p>\n" +
// "<p class=\"learn_time_label p-2\"><span>学习总时长 : </span><span style=\"color: #b9bbbe;\">?h</span>\n" +
// "</p>\n" +
// "<div class=\"d-flex flex-wrap\" style=\"width: 6rem\">\n" +
// "<button id=\"remuse_learn_btn\" class=\"btn btn-primary my-1\">继续学习</button>\n" +
// "<button id=\"delete_course_btn\" class=\"btn btn-danger my-1\">删除课程</button>\n" +
// "</div>\n" +
// "</div>";

