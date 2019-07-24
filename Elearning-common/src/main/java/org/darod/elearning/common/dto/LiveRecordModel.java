package org.darod.elearning.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/23 0023 16:40
 */
@Data
public class LiveRecordModel {
    @JSONField(serialize = false)
    private Integer liveRecordId;

    private Integer teacherId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(serialize = false)
    private Date finishTime;

    private String channelId;
//    @JSONField(serialize = false)
    private String liveSecret;

    private Integer watchNum;
    @JSONField(serialize = false)
    private Date beatTime;

    private String rtmpAddress;

}
