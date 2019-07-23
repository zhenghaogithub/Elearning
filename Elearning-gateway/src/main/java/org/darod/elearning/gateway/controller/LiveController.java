package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.darod.elearning.common.service.user.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/live/auth")
    @ApiOperation(value = "验证直播权限", httpMethod = "POST")
    public void authLive(@RequestParam HashMap<String, String> map, HttpServletResponse response) {

    }

    @PostMapping("/live/beat")
    @ApiOperation(value = "接收直播心跳", httpMethod = "POST")
    public void beatLive(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/live/done")
    @ApiOperation(value = "推流中断", httpMethod = "POST")
    public void doneLive(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/live/on_play")
    @ApiOperation(value = "增加观看人数", httpMethod = "POST")
    public void incWatchNum(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/live/on_play_done")
    @ApiOperation(value = "减少观看人数", httpMethod = "POST")
    public void decrWatchNum(@RequestParam HashMap<String, String> map, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
