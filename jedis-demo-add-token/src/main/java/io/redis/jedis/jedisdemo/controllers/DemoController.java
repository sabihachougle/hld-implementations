package io.redis.jedis.jedisdemo.controllers;

import io.redis.jedis.jedisdemo.dao.AuthToken;
import io.redis.jedis.jedisdemo.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

	@Autowired
	RedisService redisService;
	// *******************String Demo*******************//



	@PostMapping("/addToken/{username}")
	public void putKey(@PathVariable String username) {
		 redisService.putTokenKey(username);

	}

	@PostMapping("/delete/{username}")
	public void delete(@PathVariable String username, @RequestHeader(value = "Authorization") final String header) {
		redisService.delete(username);
	}

}
