package org.darod.elearning.common.dto;

import lombok.Data;
import org.darod.elearning.common.validator.MyNotBlank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 19:06
 */
@Data
public class TeacherModel {
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class,TeacherModelUpdate.class})
    private Integer teacherId;
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class,TeacherModelUpdate.class})
    private Integer userId;
    private String teacherDescription;
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class,TeacherModelUpdate.class})
    private Integer costPercent;
    @MyNotBlank(message = "教师名称不能为空", groups = {TeacherModelUpdate.class})
    @NotBlank(message = "教师名称不能为空",groups = {TeacherModelApply.class})
    private String teacherName;
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class,TeacherModelUpdate.class})
    private Date applyTime;
    @Null(message = "您无权设置部分字段", groups = {TeacherModelApply.class,TeacherModelUpdate.class})
    private Integer teacherState;


    public interface TeacherModelApply {
    }

    public interface TeacherModelUpdate {
    }
}
