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
    private Integer userId;
    private String courseName;
    private Integer teacherId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    private Double price;
    private Integer learnNum;
    private Integer courseState;
    private String firstTag;
    private String secondTag;
    private String thirdTag;
    private String courseDescription;
    private String courseImgUrl;
    private Integer courseId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date learnTime;
    private Integer lastChapter;
    private String chapterName;
}
