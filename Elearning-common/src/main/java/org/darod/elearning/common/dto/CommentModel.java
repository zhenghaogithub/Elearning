package org.darod.elearning.common.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 12:32
 */
@Data
public class CommentModel {
    private Integer commentId;

    private String commentContent;

    private Date commentTime;

    private Integer userId;

    private Integer courseId;

    private Integer chapterId;

    private Integer commentState;

    public CommentModel() {
    }

    public CommentModel(Integer commentId, String commentContent, Date commentTime, Integer userId, Integer courseId, Integer chapterId, Integer commentState) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
        this.userId = userId;
        this.courseId = courseId;
        this.chapterId = chapterId;
        this.commentState = commentState;
    }
}
