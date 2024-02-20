package io.redis.jedis.jedisdemo.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.redis.jedis.jedisdemo.dao.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public void  putTokenKey(String username){
// 9967485207 - dilip
        AuthToken token = new AuthToken();
        for (int i = 500; i <= 1000; i++) {
            String user = username + i;
            token.setToken(generateToken(user));
            token.setCreatedTime(Instant.now());
            redisTemplate.opsForValue().set(user, token);
            System.out.println("Added username : " + user);
        }
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void delete(String username) {
        for (int i = 500; i <= 1000; i++) {
            String user = username + i;
            redisTemplate.delete(username);
            System.out.println("username to expire : " + user);
        }
    }

}
