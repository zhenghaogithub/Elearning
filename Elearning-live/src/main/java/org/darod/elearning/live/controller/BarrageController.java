package org.darod.elearning.live.controller;

import org.darod.elearning.common.dto.Shout;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/8/2 0002 22:26
 */
@Controller
@CrossOrigin("*")
public class BarrageController {

    @MessageMapping("/liveroom_*")
    public Shout handle(Shout shout) {
        return shout;
    }
}



