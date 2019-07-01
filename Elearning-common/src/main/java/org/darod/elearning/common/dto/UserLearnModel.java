package org.darod.elearning.common.dto;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 8:30
 */
@Data
public class UserLearnModel {
    private Integer id;

    private Integer userId;

    private String courseId;
    @JSONField(format = "yyyy-MM-dd")
    private Date learnTime;

    private Byte lastChapter;
}
