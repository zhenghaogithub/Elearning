package org.darod.elearning.gateway.viewobject;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 15:04
 */
@Data
public class CourseLearnedVO {
    private Integer userId;
    private String courseName;
    private Integer teacherId;
    private Date publishTime;
    private Double price;
    private Integer learnNum;
    private Integer courseState;
//    private String firstTag;
//    private String secondTag;
//    private String thirdTag;
//    private String courseDescription;
    private String courseImgUrl;
//    private String courseId;
    @JSONField(format = "yyyy-MM-dd")
    private Date learnTime;
    private Integer lastChapter;
    private String chapterName;

}
