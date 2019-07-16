package org.darod.elearning.common.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:09
 */
@Data
public class ChapterModel {
    private Integer chapterId;

    private Integer chapterNo;

    private String chapterName;

    private String chapterDescription;

    private String videoUrl;

    private Date publishTime;

    private String totalTime;

    private Integer chapterState;

    private Integer courseId;
}
