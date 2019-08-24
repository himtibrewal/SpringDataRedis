package com.ayla.springdataredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSender.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    public void sendDataToRedisQueue(Student student) {
        redisTemplate.convertAndSend(topic.getTopic(), student);
        LOGGER.info("Data - Name :" + student.getName() + " Address : " + student.getAddress() + " sent through Redis Topic - " + topic.getTopic());
    }
}