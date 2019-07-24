package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.darod.elearning.common.service.user.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/21 0021 23:33
 */
@Controller
@Api(tags = "和nginx通信的直播相关")
public class LiveController {
    @Autowired
    LiveService liveService;

    private static final boolean debug_mode = false;

    @PostMapping("/live/auth")
    @ApiOperation(value = "验证直播权限", httpMethod = "POST")
    public void authLive(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
        if (debug_mode) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        if (liveService.authLive(map.get("name"), map.get("ls")))
            response.setStatus(HttpServletResponse.SC_OK);
        else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @PostMapping("/live/beat")
    @ApiOperation(value = "接收直播心跳", httpMethod = "POST")
    public void beatLive(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
        if (map.get("ls") == null) {  //play发起的update事件 不用理会
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        if (liveService.beatLive(map.get("name"), map.get("ls")))
            response.setStatus(HttpServletResponse.SC_OK);
        else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @PostMapping("/live/done")
    @ApiOperation(value = "推流中断", httpMethod = "POST")
    //用户中断推流 原则上应该直接结束直播，但用户断网或者网络波动等情况可能需要保持直播状态，可以根据情况选择是否结束
    //数据库中如果发现用户10分钟内没有推流也会自动结束直播
    public void doneLive(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
//        liveService.doneLive(map.get("name"), map.get("ls"));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/live/on_play")
    @ApiOperation(value = "增加观看人数", httpMethod = "POST")
    public void incWatchNum(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
        liveService.incWatchNum(map.get("name"));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/live/on_play_done")
    @ApiOperation(value = "减少观看人数", httpMethod = "POST")
    public void decrWatchNum(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
        liveService.decrWatchNum(map.get("name"));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/live/record_done")
    @ApiOperation(value = "封面记录完成", httpMethod = "POST")
    public void doneRecord(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
        liveService.doneRecord(map.get("name"), map.get("ls"),map.get("path"));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
