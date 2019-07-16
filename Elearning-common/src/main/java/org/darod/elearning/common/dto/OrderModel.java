package org.darod.elearning.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/13 0013 11:33
 */
@Data
public class OrderModel {
    private String orderId;

    private Integer userId;

    private Integer courseId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @Min(value = 0, message = "状态码错误")
    @Max(value = 3, message = "状态码错误")
    private Integer orderState;
}
