package io.redis.jedis.jedisdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.redis.jedis.jedisdemo.dao.AuthToken;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;

@Configuration
public class SpringConf {

    @Value("${redis.host}")
    private String host;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.port}")
    private int port;

    @Value("${redis.jedis.pool.max-total}")
    private int maxTotal;
    @Value("${redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${redis.jedis.pool.min-idle}")
    private int minIdle;

    @Bean
    public JedisClientConfiguration getJedisClientConfiguration() {
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder JedisPoolingClientConfigurationBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        JedisClientConfiguration jedisClientConfig = (JedisPoolingClientConfigurationBuilder)
                .poolConfig(genericObjectPoolConfig)
                .and().useSsl().
                and().usePooling()
                .and().
                readTimeout(Duration.ofSeconds(10))
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        return jedisClientConfig;
    }


    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        if (!StringUtils.isEmpty(password)) {
            redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        }
        redisStandaloneConfiguration.setPort(port);
        return new JedisConnectionFactory(redisStandaloneConfiguration, getJedisClientConfiguration());
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();

        AuthToken authTokenEntity = new AuthToken();
        authTokenEntity.setCreatedTime(Instant.now());
        authTokenEntity.setToken("dummyToken");

        redisTemplate.opsForValue().set("dummy_key", authTokenEntity);
        AuthToken dummykeyValue = (AuthToken) redisTemplate.opsForValue().get("dummy_key");

        return redisTemplate;
    }


    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    private Jackson2JsonRedisSerializer<AuthToken> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<AuthToken> serializer = new Jackson2JsonRedisSerializer<>(AuthToken.class);
        serializer.setObjectMapper(new ObjectMapper().registerModule(new JavaTimeModule()));
        return serializer;
    }


}
