package com.hknochi.gae;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/heartbeat")
public class HeartbeatController {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatController.class);

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index() {
        logger.info("Received heartbeat!");
        return "I'm Alive!";
    }
}
