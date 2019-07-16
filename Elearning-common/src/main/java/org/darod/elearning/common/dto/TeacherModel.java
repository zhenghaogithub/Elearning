package org.darod.elearning.common.dto;

import lombok.Data;

import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 19:06
 */
@Data
public class TeacherModel {
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class})
    private Integer teacherId;
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class})
    private Integer userId;
    private String teacherDescription;
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class})
    private Integer costPercent;
    private String teacherName;
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class})
    private Date applyTime;
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class})
    private Integer teacherState;


    public interface TeacherModelApply {
    }

    public interface groupB {
    }
}
