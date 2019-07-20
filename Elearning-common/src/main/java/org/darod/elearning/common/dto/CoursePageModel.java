package org.darod.elearning.common.dto;

import lombok.Data;
import org.darod.elearning.common.validator.EnumValue;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/9 0009 8:37
 */
@Data
public class CoursePageModel {
    @Min(value = 0, message = "页数不能为负数")
    private Integer page;
    @Min(value = 0, message = "行数不能为负数")
    private Integer row;


    //其它可选的搜索限定符
    private String sortBy;  //按某个变量排序

    private String order;   //升序还是降序 默认降序

    private String labelFirst;  //一级标签是XX

    private String labelSecond;  //二级标签是XX

    private String labelThird;  //三级标签是XX

    @Null(message = "您无权设置该字段")
    private Integer userId;  //用户ID  只有在查询和用户相关课程时候用到

    private Integer courseId;  //课程id

    private Integer teacherId;  //教师ID

    private String keyWord;   //课程名称关键字

    private String teacherName;   //老师名称关键字 模糊搜索

    @EnumValue(enumClass = PriceRange.class)
    private String priceRange;  //价格区间

    public enum PriceRange {
        _FREE,//免费
        _0T49,//0到49
        _50T99, //50到99
        _100T299, //100到299
        _300T499,  //300到500
        _500 //500以上
    }

    public void checkPage() {
        if (page == null || row == null) {
            page = 0;
            row = 9999;
        }
    }
}
