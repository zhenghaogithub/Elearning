package org.darod.elearning.gateway.serviceimpl;

import org.darod.elearning.common.dto.LiveRecordModel;
import org.darod.elearning.common.dto.LiveRoomModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.LiveService;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.gateway.dao.LiveRecordDOMapper;
import org.darod.elearning.gateway.dao.LiveRoomDOMapper;
import org.darod.elearning.gateway.dataobject.LiveRecordDO;
import org.darod.elearning.gateway.dataobject.LiveRoomDO;
import org.darod.elearning.gateway.utils.RandomUtils;
import org.darod.elearning.gateway.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/22 0022 15:24
 */
@Service
public class LiveServiceImpl implements LiveService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private LiveRoomDOMapper liveRoomDOMapper;
    @Autowired
    private LiveRecordDOMapper liveRecordDOMapper;

    @Override
    @Transactional
    public LiveRecordModel addLive(Integer teacherId) {
        //查看用户是否正在直播  锁表防止用户同时开启两个直播 如果有性能问题时可以优化一下
        List<LiveRecordDO> liveRecordDOS = liveRecordDOMapper.getCurLiveRecordByTeacherIdForUpdate(teacherId);
        if (liveRecordDOS.size() > 0) {
            return CopyPropertiesUtils.copyProperties(liveRecordDOS.get(0), LiveRecordModel.class);
        }
        LiveRecordDO liveRecordDO = new LiveRecordDO();
        String randomChannelId;
        do {
            randomChannelId = RandomUtils.getRandomChannelId();
        } while (redisUtils.keys(randomChannelId + "?").size() > 0
                || liveRecordDOMapper.getCurLiveRecordByChannel(randomChannelId, false) != null); //生成随机频道ID,直到没有重复
        String randomLiveSecret = RandomUtils.getRandomLiveSecret();
        liveRecordDO.setTeacherId(teacherId);
        liveRecordDO.setChannelId(randomChannelId);
        liveRecordDO.setLiveSecret(randomLiveSecret);
        liveRecordDOMapper.insertSelective(liveRecordDO);
        LiveRecordDO liveRecordDO1 = liveRecordDOMapper.selectByPrimaryKey(liveRecordDO.getLiveRecordId());
        redisUtils.set(getLiveKeyInRedis(randomChannelId, randomLiveSecret), liveRecordDO1, 5 * 60); //5分钟后失效
        return CopyPropertiesUtils.copyProperties(liveRecordDO1, LiveRecordModel.class);
    }

    @Override
    public void stopLive(Integer teacherId) {
        List<LiveRecordDO> liveRecordDOS = liveRecordDOMapper.getCurLiveRecordByTeacherId(teacherId);
        if (liveRecordDOS.size() <= 0) {
            throw new BusinessException(EmException.LIVE_HAVE_STOP);
        } else if (liveRecordDOS.size() > 1) {
            throw new BusinessException(EmException.UNKNOWN_ERROR, "当前直播数多于一个,请联系管理员");
        }
        LiveRecordDO liveRecordDO = new LiveRecordDO();
        liveRecordDO.setLiveRecordId(liveRecordDOS.get(0).getLiveRecordId());
        liveRecordDO.setFinishTime(new Date());
        liveRecordDOMapper.updateByPrimaryKeySelective(liveRecordDO);
        redisUtils.del(getLiveKeyInRedis(liveRecordDOS.get(0).getChannelId(), liveRecordDOS.get(0).getLiveSecret()));
    }

    @Override
    public LiveRoomModel updateLiveRoom(LiveRoomModel liveRoomModel) {
        return CopyPropertiesUtils.copyAndInsertThenReturn(liveRoomModel, LiveRoomDO.class, liveRoomDOMapper::updateByTeacherIdSelective,
                x -> liveRoomDOMapper.selectByTeacherId(x.getTeacherId()));
    }

    @Transactional
    @Override
    public boolean authLive(String channelId, String liveSecret) {
        Object o = redisUtils.get(getLiveKeyInRedis(channelId, liveSecret));
        if (o == null) return false;  //缓存里没有 则失效

        LiveRecordDO liveRecordDO = liveRecordDOMapper.getCurLiveRecordByChannelAndSecretForUpdate(channelId, liveSecret);
        if (liveRecordDO == null || liveRecordDO.getBeatTime() != null) return false; //一个推流码只能使用一次
        liveRecordDO.setBeatTime(new Date());
        liveRecordDOMapper.updateByPrimaryKeySelective(liveRecordDO);
        return true;
    }

    @Override
    public boolean beatLive(String channelId, String liveSecret) {
        LiveRecordDO liveRecordDO = liveRecordDOMapper.getCurLiveRecordByChannelAndSecret(channelId, liveSecret);
        if (liveRecordDO == null) return false;
        liveRecordDO.setBeatTime(new Date());
        liveRecordDOMapper.updateByPrimaryKeySelective(liveRecordDO);
        return true;
    }

    @Override
    public void doneLive(String channelId, String liveSecret) {
        LiveRecordDO liveRecordDO = liveRecordDOMapper.getCurLiveRecordByChannelAndSecret(channelId, liveSecret);
        if (liveRecordDO == null) return;
        liveRecordDO.setFinishTime(new Date());
        liveRecordDOMapper.updateByPrimaryKeySelective(liveRecordDO);
    }

    @Override
    @Transactional
    public void incWatchNum(String channelId) {
        LiveRecordDO liveRecordDO = liveRecordDOMapper.getCurLiveRecordByChannel(channelId, true);
        if (liveRecordDO == null) return;
        liveRecordDO.setWatchNum(liveRecordDO.getWatchNum() + 1);
        liveRecordDOMapper.updateByPrimaryKeySelective(liveRecordDO);
    }

    @Override
    @Transactional
    public void decrWatchNum(String channelId) {
        LiveRecordDO liveRecordDO = liveRecordDOMapper.getCurLiveRecordByChannel(channelId, true);
        if (liveRecordDO == null) return;
        liveRecordDO.setWatchNum(liveRecordDO.getWatchNum() - 1);
        liveRecordDOMapper.updateByPrimaryKeySelective(liveRecordDO);
    }


    private LiveRecordModel getCurLiveRecordModelFromCache(String channelId, String liveSecret) {
        return CopyPropertiesUtils.copyProperties(getCurLiveRecordDOFromCache(channelId, liveSecret), LiveRecordModel.class);
    }

    private LiveRecordDO getCurLiveRecordDOFromCache(String channelId, String liveSecret) {
        String key = getLiveKeyInRedis(channelId, liveSecret);
        Object o = redisUtils.get(key);
        if (o instanceof LiveRecordDO) {
            return (LiveRecordDO) o;
        }
        LiveRecordDO liveRecordDO = liveRecordDOMapper.getCurLiveRecordByChannelAndSecret(channelId, liveSecret);
        if (liveRecordDO == null) { //直播已停止
            redisUtils.del(key);
            return null;
        }
        redisUtils.set(key, liveRecordDO, 60); //1分钟过期
        return liveRecordDO;
    }

    private String getLiveKeyInRedis(String channelId, String secret) {
        return channelId + "?" + secret;
    }
}
