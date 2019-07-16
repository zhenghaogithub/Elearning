package org.darod.elearning.common.dto;

import lombok.Data;
import org.darod.elearning.common.validator.EnumValue;

import javax.validation.constraints.Min;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/15 0015 13:53
 */
@Data
public class CommonPageModel {
    @Min(value = 0, message = "页数不能为负数")
    private Integer page;
    @Min(value = 0, message = "行数不能为负数")
    private Integer row;

    //其它可选的搜索限定符
    private String sortBy;  //按某个变量排序

    @EnumValue(enumClass = OrderType.class)
    private String order;   //升序还是降序 默认降序

    public enum OrderType {
        ASC,
        DESC
    }

    public void checkPage() {
        if (page == null || row == null) {
            page = 0;
            row = 9999;
        }
    }
}
