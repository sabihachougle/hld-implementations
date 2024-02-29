package io.redis.jedis.jedisdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JedisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JedisDemoApplication.class, args);
	}
}
