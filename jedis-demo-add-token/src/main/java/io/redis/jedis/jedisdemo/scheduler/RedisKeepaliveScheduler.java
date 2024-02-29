package io.redis.jedis.jedisdemo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RedisKeepaliveScheduler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedRate = 60000) // Adjust the rate based on your requirements
    public void sendPing() {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        try {
            connection.ping();
            System.out.println("pinged Redis");
        } finally {
            connection.close();
        }
    }
}
