package io.redis.jedis.jedisdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.redis.jedis.jedisdemo.dao.AuthToken;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
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
	public LettuceConnectionFactory lettuceConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		if (!StringUtils.isEmpty(password)) {
			redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
		}
		redisStandaloneConfiguration.setPort(port);

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
				.clientOptions(ClientOptions.builder().socketOptions(SocketOptions.builder()
								.keepAlive(true)  // Enable keep-alive
								.build())
						.build())
				.commandTimeout(Duration.ofSeconds(10))
				.build();

		return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
	}

	@Bean("redisTemplate")
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory());
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

	private Jackson2JsonRedisSerializer<AuthToken> jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<AuthToken> serializer = new Jackson2JsonRedisSerializer<>(AuthToken.class);
		serializer.setObjectMapper(new ObjectMapper().registerModule(new JavaTimeModule()));
		return serializer;
	}

}
