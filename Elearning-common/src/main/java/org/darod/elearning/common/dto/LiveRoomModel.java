package org.darod.elearning.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/22 0022 15:20
 */
@Data
public class LiveRoomModel {
    private Integer userId;

    private Integer teacherId;

    private String roomName;

    private String roomDescription;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLiveTime;

    private String roomImage;
}
