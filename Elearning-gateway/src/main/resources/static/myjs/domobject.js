$(document).ready(function () {
    $.extend({
        bindModel: function (dom, model) {
            if (dom == null || model == null) return;
            dom.find("*[model]").each(function () {
                const tagName = $(this)[0].tagName;
                const propertyName = $(this).attr("model");
                if (model[propertyName] == null) return;
                if ($(this)[0].hasAttribute("model_target")) {
                    $(this).attr($(this).attr("model_target"), model[propertyName]);
                } else if (tagName === "INPUT" || tagName === "TEXTAREA" || tagName === "SELECT") {
                    $(this).val(model[propertyName]);
                } else {
                    $(this).text(model[propertyName]);
                }
            });
            dom.find("*[model_class]").each(function () {
                let clazz = $(this).attr("model_class");
                if (clazz != null) {
                    $(this).addClass(model[clazz]);
                }
            });
            return dom[0];
        },
        bindModelReverse: function (dom) {
            if (dom == null) return;
            if (dom[0] == null) return dom; //不是Jquery对象，则代表已经映射好了 直接返回
            let model = {};
            dom.find("*[model]").each(function () {
                const tagName = $(this)[0].tagName;
                const propertyName = $(this).attr("model");
                // if (model[propertyName] == null) return;
                if ($(this)[0].hasAttribute("model_target")) {
                    model[propertyName] = $(this).attr($(this).attr("model_target"));
                } else if (tagName === "INPUT" || tagName === "TEXTAREA" || tagName === "SELECT") {
                    model[propertyName] = $(this).val();
                } else {
                    model[propertyName] = $(this).text();
                }
            });
            // dom.find("*[model_class]").each(function () {
            //     let clazz = $(this).attr("model_class");
            //     if (clazz != null) {
            //         $(this).addClass(model[clazz]);
            //     }
            // });
            return model;
        },
        mapProperties: function (object, mapper) {
            for (let property in mapper) {
                if (mapper.hasOwnProperty(property)) {
                    let mapperObject = mapper[property];
                    if (mapperObject != null) {
                        let mapValue = mapperObject[object[property]];
                        if (mapValue != null)
                            object[property] = mapValue;
                        else
                            object[property] = "unknown";
                    }
                }
            }
        },
        mapAndAddProperties: function (object, mapper) {
            for (let property in mapper) {
                if (mapper.hasOwnProperty(property)) {
                    let mapperObject = mapper[property];
                    if (mapperObject != null) {
                        let targetProperty = mapperObject.target;
                        let realMapper = mapperObject.mapper;
                        if (targetProperty != null && realMapper != null) {
                            let mapValue = realMapper[object[property]];
                            if (mapValue != null) {
                                if (!(targetProperty in object))
                                    object[targetProperty] = mapValue;
                            } else {
                                object[targetProperty] = "unknown";
                            }
                        }
                    }
                }
            }
        },
        courses_info_item: " <div class=\"courses_info_item item  d-flex p-0  border-bottom justify-content-around\">\n" +
            "                        <img model='courseImage' model_target='src' class=\"course_img\"\n" +
            "                            >\n" +
            "                        <div class=\"course_info_desc p-3\">\n" +
            "                            <p>\n" +
            "                                <strong model='courseName' class=\"course_name_label\"></strong>\n" +
            "                                <span class=\"course_status\">学习中</span>\n" +
            "                            </p>\n" +
            "                            <p>\n" +
            "<!--                                <span class=\"learn_percent\">进度:23%</span>-->\n" +
            "                                <span>上次学到 : </span>\n" +
            "                                <span model='lastChapterName' class=\"last_chapter_label\" style=\"color: #b9bbbe;\"></span>\n" +
            "                            </p>\n" +
            "                        </div>\n" +
            "                        <p class=\"start_time_label p-2\"><span>添加时间 : </span><span\n" +
            "                                style=\"color:#b9bbbe;\" model='learnTime'></span></p>\n" +
            "<!--                        <p class=\"learn_time_label p-2\"><span>学习总时长 : </span><span style=\"color: #b9bbbe;\">23h</span>-->\n" +
            "<!--                        </p>-->\n" +
            "                        <div model='courseId' model_target='courseId'  class=\"d-flex flex-wrap justify-content-around button-div\" style=\"width: 6rem\">\n" +
            "                            <button href='watchVideo.html' model='lastChapterId' model_target='lastChapterId'  id=\"remuse_learn_btn\" class=\"btn btn-primary my-1\" onclick='continue_learn_btn_click(this)'>继续学习</button>\n" +
            // "                            <button id=\"delete_course_btn\" class=\"btn btn-danger my-1\" onclick='delete_learned_course_btn_click(this);'>删除课程</button>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>",
        courses_published_item: "<div class=\"courses_published_item item d-flex p-0  border-bottom justify-content-around\">\n" +
            "                        <img  model='courseImage' model_target='src' class=\"course_img\"\n" +
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
            "                            <div class=\"d-flex justify-content-between course_learn_info\">\n" +
            "                                <span>课程状态 : <span model_class='courseStateColor' model='courseState'  class=\"course_state_open green_font_color\">开放中</span></span>\n" +
            "                                <span>价格 : <span  model='cost'  class=\"course_price\"></span></span>\n" +
            "                                <span>学习人数 : <span model='learnTotal' class=\"learn_count gray_font_color\"></span></span>\n" +
            "                                <span>评论 : <span model='comment_count' class=\"comment_count gray_font_color\">0</span></span>\n" +
            "                                <!--                                <span class=\"last_time_label\">3 : 12</span>-->\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <p class=\"start_time_label p-2\"><span>发布时间 : </span><span\n" +
            "                                class=\"gray_font_color\"  model='courseTime' ></span></p>\n" +
            "                        <div class=\"d-flex flex-wrap justify-content-around button-div\" style=\"width: 6rem\">\n" +
            "                            <a model='course_url' model_target='href' target='_blank' href='' id=\"course_info_btn\"  class=\"btn btn-primary my-1\">课程详细</a>\n" +
            "                            <button model='courseId' model_target='courseId' id=\"withdraw_course_btn\" class=\"btn btn-danger my-1\" onclick='delete_published_course_btn_click(this);'>下架课程</button>\n" +
            "                        </div>\n" +
            "                    </div>",
        order_info_item: "<div class=\"order_info_item item d-flex p-0  border-bottom justify-content-around\">\n" +
            "                        <img  model='courseImage' model_target='src' class=\"course_img\"\n" +
            "                            >\n" +
            "                        <div class=\"order_info_desc p-3\">\n" +
            "                            <p>\n" +
            "                                <strong model='courseName'  class=\"course_name_label\"></strong>\n" +
            "                            </p>\n" +
            "                            <div class=\"d-flex justify-content-between\">\n" +
            // "                                <span>订单号 : <span class=\"gray_font_color\">2019061100001</span></span>\n" +
            "                                <span>订单状态 : <span model='orderState' model_class='orderStateColor'  class=\"course_state_open\"></span></span>\n" +
            "                                <span>价格 : <span model='cost' class=\"course_price\"></span></span>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <p class=\"start_time_label p-2\"><span>下单时间 : </span><span\n" +
            "                                style=\"color:#b9bbbe;\" model='learnTime'></span></p>\n" +
            "                        </p>\n" +
            "                        <div class=\"d-flex flex-wrap justify-content-around button-div\" style=\"width: 6rem\">" +
            "                           <button id=\"evaluate_btn\" class=\"btn btn-warning my-1\">评价课程</button>" +
            "                        </div>" +
            "                    </div>",
        chapter_info_item: "<div class=\"swiper-slide\">\n" +
            "                <div model='chapterId' model_target='chapter_id' class=\"position-relative\">\n" +
            "                    <span model=\"chapterNumber\" class=\"course_chapter_num h5\">第1章</span>\n" +
            "                    <span model=\"chapterState\" model_class='chapterStateColor' class=\"chapter_state_label\">开放中</span>\n" +
            "                    <div class=\"chapter_info\">\n" +
            "                        <div class=\"chapter_name_div d-flex align-items-center justify-content-center\">\n" +
            "                            <div model=\"chapterName\" class=\"chapter_name\">\n" +
            "                                ASP.NET-MVC课程介绍\n" +
            "                            </div>\n" +
            "                            <div class='chapter_introduction' model='chapterIntroduction' style='display: none'></div>\n" +  //隐藏用来存储章节介绍
            "                        </div>\n" +
            // "                        <div model=\"chapterLength\" class=\"chapter_length\">3:12</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>",
    });
});


// "<div class=\"course_info_item  d-flex p-0  border-bottom justify-content-around\">\n" +
// "<img class=\"course_img\"\n" +
// "src=\"" + i.courseImage + "\">\n" +
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

