package org.darod.elearning.common.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 16:00
 */
@Data
public class MessageModel {
    public MessageModel() {
    }

    public MessageModel(Integer messageId, Integer messageType, Date messageTime, String messageContent, Integer messageState, Integer senderId, Integer receiverId) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.messageTime = messageTime;
        this.messageContent = messageContent;
        this.messageState = messageState;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    private Integer messageId;

    private Integer messageType;

    private Date messageTime;

    private String messageContent;

    private Integer messageState;

    private Integer senderId;

    private Integer receiverId;

}
