package org.darod.elearning.gateway.advice;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.darod.elearning.common.dto.LiveRecordModel;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.exception.UploadException;
import org.darod.elearning.gateway.controller.LiveController;
import org.darod.elearning.gateway.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/23 0023 14:23
 */
@Aspect
@Component
public class LiveAdvice {
    @Autowired
    RedisUtils redisUtils;
    @Value("${org.darod.elearning.gateway.live_key}")
    public  String the_key; //验证密钥，只有密钥正确的请求才能增加\减少播放量
    @Value("${org.darod.elearning.gateway.rtmp_address}")
    private String rtmp_address;

    @Pointcut("bean(liveController)")
    void liveMethod() {
    }

    //验证key是否正确
    @Before("liveMethod() && args(map,..)")
    public void checkLiveKey(HashMap<String, String> map) {
        if (map == null || StringUtils.isEmpty(map.get("key"))) {
            throw new UploadException(EmException.PERMISSION_DENIED, "禁止访问");
        }
        if (!map.get("key").equals(the_key)) {
            throw new UploadException(EmException.PERMISSION_DENIED, "禁止访问");
        }
    }

    //自动设置rtmp地址
    @AfterReturning(returning = "liveRecordModel", pointcut = "bean(liveServiceImpl) && execution(public org.darod.elearning.common.dto.LiveRecordModel *(..))")
    public void setRtmp_address(LiveRecordModel liveRecordModel) {
        liveRecordModel.setRtmpAddress(rtmp_address);
    }

    //更新数据库后删除缓存
    @AfterReturning("bean(liveServiceImpl) && args(channelId)")
    public void deleteLiveRecordCache(String channelId) {
        deleteRedisCache(channelId, null);
    }

    //更新数据库后删除缓存
    @AfterReturning("bean(liveServiceImpl) && args(channelId,liveSecret) && execution(public * *.*(..))")
    public void deleteLiveRecordCache(String channelId, String liveSecret) {
        deleteRedisCache(channelId, liveSecret);
    }

    private void deleteRedisCache(String channelId, String liveSecret) {
        if (liveSecret != null) {
            redisUtils.del(channelId + "?" + liveSecret);
        } else {
            redisUtils.keys(channelId + "?").forEach(redisUtils::del);
        }
    }
}
