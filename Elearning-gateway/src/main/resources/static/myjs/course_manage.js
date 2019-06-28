$(document).ready(function () {
    //为章节div添加点击事件
    //测试方法
    function addChapterDivClickEvent(index, element) {
        $(element).on("click", function () {
            const modal = $("#edit_chapter_modal");
            modal.attr("chapter_id", $(this).attr("chapter_id")); //在模态框上记录点击的章节ID
            modal.modal("show");  //显示模态框

            //把章节数据填入表格  需要ajax 以后写
            $("#chapter_name_label_edit").val($(this).find(".chapter_name").text().trim());
            //...

        })
    }


    $(".swiper-slide").each(addChapterDivClickEvent);
    var swiper = new Swiper('.swiper-container', {
        slidesPerView: 5,
        spaceBetween: 15,
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        observer: true,
        observeParents: true,
        breakpoints: {
            1024: {
                slidesPerView: 4,
                spaceBetween: 40,
            },
            768: {
                slidesPerView: 3,
                spaceBetween: 30,
            },
            640: {
                slidesPerView: 2,
                spaceBetween: 20,
            },
            320: {
                slidesPerView: 1,
                spaceBetween: 10,
            }
        }
    });
});

