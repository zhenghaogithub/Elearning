package org.darod.elearning.common.dto;

import lombok.Data;

import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 15:00
 */
@Data
public class CourseModel {
    @Null(message = "您无权设置某些字段", groups = CourseModelForCreate.class)
    private Integer courseId;
    private String courseName;
    @Null(message = "您无权设置某些字段", groups = CourseModelForCreate.class)
    private Integer teacherId;
    private Date publishTime;
    private Double price;
    @Null(message = "您无权设置某些字段", groups = CourseModelForCreate.class)
    private Integer learnNum;
    @Null(message = "您无权设置某些字段", groups = CourseModelForCreate.class)
    private Integer courseState;
    private String firstTag;
    private String secondTag;
    private String thirdTag;
    private String courseDescription;
    @Null(message = "您无权设置某些字段", groups = CourseModelForCreate.class)
    private String courseImgUrl;

    public interface CourseModelForCreate {
    }
}
