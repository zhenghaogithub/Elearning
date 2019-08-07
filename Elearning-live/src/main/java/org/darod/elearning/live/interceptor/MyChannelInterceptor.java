package org.darod.elearning.live.interceptor;

import org.apache.dubbo.config.annotation.Reference;
import org.darod.elearning.common.service.user.LiveService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/8/7 0007 23:44
 */
public class MyChannelInterceptor implements ChannelInterceptor {

    @Reference
    LiveService liveService;

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        if (StompCommand.SUBSCRIBE.equals(command)) {
            liveService.incWatchNum(getChannelIdFromDestination(accessor.getDestination())); //连接建立成功 增加观看人数
        }
        if (StompCommand.DISCONNECT.equals(command)) {
            liveService.decrWatchNum(getChannelIdFromDestination(accessor.getDestination())); //断开后减少观看人数
        }
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        if (StompCommand.SUBSCRIBE.equals(command)) {
            String channelId = getChannelIdFromDestination(accessor.getDestination());
            if (liveService.isLiveExist(channelId)) {
                return message;
            } else {
                return null;
            }
        } else {
            return message;
        }
    }

    private String getChannelIdFromDestination(String des) {
        if (des == null) return "";
        return des.substring(des.lastIndexOf("/") + 1);
    }
}
