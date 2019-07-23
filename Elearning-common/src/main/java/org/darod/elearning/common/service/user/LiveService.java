package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.LiveRecordModel;
import org.darod.elearning.common.dto.LiveRoomModel;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/21 0021 23:34
 */
public interface LiveService {

    LiveRecordModel addLive(Integer teacherId);

    void stopLive(Integer teacherId);

    LiveRoomModel updateLiveRoom(LiveRoomModel liveRoomModel);

    boolean authLive(String channelId, String liveSecret);

    boolean beatLive(String channelId, String liveSecret);

    void doneLive(String channelId, String liveSecret);

    void incWatchNum(String channelId);

    void decrWatchNum(String channelId);

    LiveRecordModel getCurLiveRecordModelFromCache(String channelId, String liveSecret);
}
