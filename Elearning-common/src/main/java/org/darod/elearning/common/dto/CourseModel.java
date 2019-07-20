package org.darod.elearning.common.dto;

import lombok.Data;
import org.darod.elearning.common.validator.MyNotBlank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 15:00
 */
@Data
public class CourseModel {
    @Null(message = "您无权设置某些字段", groups = {CourseModelForCreate.class, CourseModelForUpdate.class})
    private Integer courseId;
    @MyNotBlank(message = "课程名不能为空", groups = {CourseModelForUpdate.class})
    @NotBlank(message = "课程名不能为空", groups = {CourseModelForCreate.class})
    private String courseName;
    @Null(message = "您无权设置某些字段", groups = {CourseModelForCreate.class, CourseModelForUpdate.class})
    private Integer teacherId;
    private Date publishTime;
    private Double price;
    @Null(message = "您无权设置某些字段", groups = {CourseModelForCreate.class, CourseModelForUpdate.class})
    private Integer learnNum;
    @Null(message = "您无权设置某些字段", groups = {CourseModelForCreate.class, CourseModelForUpdate.class})
    private Integer courseState;
    private String firstTag;
    private String secondTag;
    private String thirdTag;
    private String courseDescription;
    @Null(message = "您无权设置某些字段", groups = {CourseModelForCreate.class, CourseModelForUpdate.class})
    private String courseImgUrl;

    public interface CourseModelForCreate {
    }

    public interface CourseModelForUpdate {
    }
}
