package io.redis.jedis.jedisdemo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.redis.jedis.jedisdemo.dao.AuthToken;
import io.redis.jedis.jedisdemo.model.Programmer;
import io.redis.jedis.jedisdemo.services.ProgrammerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class DemoController {

	@Autowired
	ProgrammerService programmerService;
	// *******************String Demo*******************//



	@PostMapping("/addToken")
	public AuthToken putKey() {
		return programmerService.putTokenKey();

	}

}
