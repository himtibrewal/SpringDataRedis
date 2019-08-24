package com.ayla.springdataredis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;


@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${topic}")
    private String redisTopic;


    @Bean
    JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        return factory;
    }

    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisJsonSerializer() {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisJsonSerializer =
                new GenericJackson2JsonRedisSerializer();
        return genericJackson2JsonRedisJsonSerializer;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setValueSerializer(genericJackson2JsonRedisJsonSerializer());
        return template;
    }


    @Bean
    ChannelTopic topic() {
        return new ChannelTopic(redisTopic);
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addMessageListener(new MessageListenerAdapter(new RedisReciever()), topic());
        // container.setTaskExecutor(Executors.newFixedThreadPool(4));
        return container;
    }

}