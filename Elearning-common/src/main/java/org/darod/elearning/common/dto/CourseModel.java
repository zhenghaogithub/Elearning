package org.darod.elearning.common.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 15:00
 */
@Data
public class CourseModel {
    private Integer id;
    private String courseName;
    private Integer publisherId;
    private Date publishTime;
    private Double price;
    private Integer learnNum;
    private Byte courseState;
    private String firstTag;
    private String secondTag;
    private String thirdTag;
    private String courseDescription;
    private String courseImgUrl;
}
