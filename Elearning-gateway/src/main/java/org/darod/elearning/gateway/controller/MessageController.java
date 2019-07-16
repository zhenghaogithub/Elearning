package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import org.darod.elearning.common.dto.CommonPageModel;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.gateway.serviceimpl.MessageServiceImpl;
import org.darod.elearning.gateway.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/16 0016 15:16
 */
@RestController
@Api(tags = "消息接口")
public class MessageController {
    @Autowired
    MessageServiceImpl messageService;

    @GetMapping("/user/message")
    public CommonResponse getAllMessage(@Validated CommonPageModel commonPageModel) {
        commonPageModel.checkPage();
        return ResponseUtils.getOKResponse(messageService.getAllMessage(ShiroUtils.getCurUserId(), commonPageModel).toJSONObject("messages"));
    }

    @GetMapping("/user/message/{messageId}")
    public CommonResponse getMessageById(@PathVariable("messageId") Integer messageId) {
        return ResponseUtils.getOKResponse(messageService.getMessageById(ShiroUtils.getCurUserId(), messageId));
    }

    @GetMapping("/user/message/unread")
    public CommonResponse getMessagesUnread(@Validated CommonPageModel commonPageModel) {
        commonPageModel.checkPage();
        return ResponseUtils.getOKResponse(messageService.getMessagesUnread(ShiroUtils.getCurUserId(), commonPageModel).toJSONObject("messages"));

    }

    @GetMapping("/user/message/unread_count")
    public CommonResponse getMessagesUnreadCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("total", messageService.getMessagesUnreadCount(ShiroUtils.getCurUserId()));
        return ResponseUtils.getOKResponse(map);
    }

    @DeleteMapping("/user/message/{messageId}")
    public CommonResponse deleteMessage(@PathVariable("messageId") Integer messageId) {
        messageService.deleteMessage(ShiroUtils.getCurUserId(), messageId);
        return ResponseUtils.getOKResponse();
    }

    @DeleteMapping("/user/message/read")
    public CommonResponse deleteAllMessage() {
        messageService.deleteAllMessage(ShiroUtils.getCurUserId());
        return ResponseUtils.getOKResponse();
    }

    @PutMapping("/user/message/{messageId}")
    public CommonResponse setMessageRead(@PathVariable("messageId") Integer messageId) {
        return ResponseUtils.getOKResponse(messageService.setMessageRead(ShiroUtils.getCurUserId(), messageId));
    }

    @PutMapping("/user/message")
    public CommonResponse setAllMessageRead() {
        messageService.setAllMessageRead(ShiroUtils.getCurUserId());
        return ResponseUtils.getOKResponse();
    }


}
