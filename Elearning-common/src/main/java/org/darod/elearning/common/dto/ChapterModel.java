package org.darod.elearning.common.dto;

import lombok.Data;
import org.darod.elearning.common.validator.MyNotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:09
 */
@Data
public class ChapterModel {
    @Null(message = "您无权设置部分字段", groups = {ChapterModelForCreate.class, ChapterModelForUpdate.class})
    private Integer chapterId;
    @Min(value = 1, message = "章节编号不能小于1", groups = {ChapterModelForCreate.class, ChapterModelForUpdate.class})
    @NotNull(message = "章节序号不能为空", groups = ChapterModelForCreate.class)
    private Integer chapterNo;
    @NotBlank(message = "章节名称不能为空", groups = ChapterModelForCreate.class)
    @MyNotBlank(message = "章节名称不能为空", groups = ChapterModelForUpdate.class)
    private String chapterName;

    private String chapterDescription;
    @Null(message = "您无权设置部分字段", groups = {ChapterModelForCreate.class, ChapterModelForUpdate.class})
    private String videoUrl;
    @Null(message = "您无权设置部分字段", groups = {ChapterModelForCreate.class, ChapterModelForUpdate.class})
    private Date publishTime;
    @Null(message = "您无权设置部分字段", groups = {ChapterModelForCreate.class, ChapterModelForUpdate.class})
    private String totalTime;
    @Null(message = "您无权设置部分字段", groups = {ChapterModelForCreate.class, ChapterModelForUpdate.class})
    private Integer chapterState;
    @Null(message = "您无权设置部分字段", groups = {ChapterModelForCreate.class, ChapterModelForUpdate.class})
    private Integer courseId;

    public interface ChapterModelForCreate {
    }

    public interface ChapterModelForUpdate {
    }
}
