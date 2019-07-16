package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CommonPageModel;
import org.darod.elearning.common.dto.MessageModel;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 15:17
 */
public interface MessageService {
    CommonCountModel<List<MessageModel>> getAllMessage(Integer userId, CommonPageModel commonPageModel);

    MessageModel getMessageById(Integer userId, Integer messageId);

    CommonCountModel<List<MessageModel>> getMessagesUnread(Integer userId, CommonPageModel commonPageModel);

    Integer getMessagesUnreadCount(Integer userId);

    void deleteMessage(Integer userId, Integer messageId);

    void deleteAllMessage(Integer userId);

    void deleteAllMessageRead(Integer userId);

    MessageModel setMessageRead(Integer userId, Integer messageId);

    void setAllMessageRead(Integer userId);

}
