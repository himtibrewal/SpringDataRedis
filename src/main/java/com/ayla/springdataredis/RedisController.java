package com.ayla.springdataredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisSender sender;

    @PostMapping
    public String sendDataToRedisQueue(@RequestBody Student student) {
        sender.sendDataToRedisQueue(student);
        return "successfully sent";
    }
}