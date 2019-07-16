package org.darod.elearning.gateway.serviceimpl;

import com.github.pagehelper.PageHelper;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CommonPageModel;
import org.darod.elearning.common.dto.MessageModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.MessageService;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.gateway.dao.MessageDOMapper;
import org.darod.elearning.gateway.dataobject.MessageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 15:17
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDOMapper messageDOMapper;

    @Override
    public CommonCountModel<List<MessageModel>> getAllMessage(Integer userId, CommonPageModel commonPageModel) {
        return CommonCountModel.getCountModelFromList(PageHelper.startPage(commonPageModel.getPage(), commonPageModel.getRow()),
                messageDOMapper.getAllMessage(userId, commonPageModel), MessageModel.class);
    }

    @Override
    public MessageModel getMessageById(Integer userId, Integer messageId) {
        MessageDO messageDO = messageDOMapper.getMessageById(messageId);
        if (!messageDO.getReceiverId().equals(userId)) {
            throw new BusinessException(EmException.PERMISSION_DENIED, "您无权访问该消息");
        }
        return CopyPropertiesUtils.copyProperties(messageDO, MessageModel.class);
    }

    @Override
    public CommonCountModel<List<MessageModel>> getMessagesUnread(Integer userId, CommonPageModel commonPageModel) {
        return CommonCountModel.getCountModelFromList(PageHelper.startPage(commonPageModel.getPage(), commonPageModel.getRow()),
                messageDOMapper.getMessagesUnread(userId, commonPageModel), MessageModel.class);
    }

    @Override
    public Integer getMessagesUnreadCount(Integer userId) {
        return messageDOMapper.getMessagesUnreadCount(userId);
    }

    @Override
    public void deleteMessage(Integer userId, Integer messageId) {
        MessageDO messageDO = messageDOMapper.selectByPrimaryKey(messageId);
        if (!messageDO.getReceiverId().equals(userId)) {
            throw new BusinessException(EmException.PERMISSION_DENIED, "您无权删除该消息");
        }
        messageDOMapper.deleteMessage(messageId);
    }

    @Override
    public void deleteAllMessage(Integer userId) {
        messageDOMapper.deleteAllMessage(userId);
    }

    @Override
    public void deleteAllMessageRead(Integer userId) {
        messageDOMapper.deleteAllMessageRead(userId);
    }

    @Override
    public MessageModel setMessageRead(Integer userId, Integer messageId) {
        MessageDO messageDO = messageDOMapper.selectByPrimaryKey(messageId);
        if (!messageDO.getReceiverId().equals(userId)) {
            throw new BusinessException(EmException.PERMISSION_DENIED, "您无权修改该消息");
        }
        messageDOMapper.setMessageRead(messageId);
        return CopyPropertiesUtils.copyProperties(messageDOMapper.selectByPrimaryKey(messageId), MessageModel.class);
    }

    @Override
    public void setAllMessageRead(Integer userId) {
        messageDOMapper.setAllMessageRead(userId);
    }
}
