package io.redis.jedis.jedisdemo.dao;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.io.Serializable;
import java.time.Instant;

@EnableRedisRepositories
public class AuthToken implements Serializable {
    String token;
    Instant createdTime;


    public AuthToken(String token, Instant createdTime) {
        this.token = token;
        this.createdTime = createdTime;
    }

    public AuthToken() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }
}
