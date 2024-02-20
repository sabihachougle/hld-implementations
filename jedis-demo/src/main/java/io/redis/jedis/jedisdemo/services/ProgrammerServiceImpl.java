package io.redis.jedis.jedisdemo.services;

import java.security.Key;
import java.time.Instant;
import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.redis.jedis.jedisdemo.dao.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.redis.jedis.jedisdemo.dao.ProgrammerRepository;
import io.redis.jedis.jedisdemo.model.Programmer;

@Service
public class ProgrammerServiceImpl implements ProgrammerService{

	@Autowired
	ProgrammerRepository repository;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	
	@Override
	public void setProgrammerAsString(String idKey, String programmer) {
		repository.setProgrammerAsString(idKey,programmer);
		
	}

	@Override
	public String getProgrammerAsString(String key) {
		return 	repository.getProgrammerAsString(key);
	}

	  //List
	@Override
	public void AddToProgrammersList(Programmer programmer) {
		repository.AddToProgrammersList(programmer);
		
	}

	@Override
	public List<Programmer> getProgrammersListMembers() {
		return  repository.getProgrammersListMembers();
	}

	@Override
	public Long getProgrammersListCount() {
		return repository.getProgrammersListCount();
	}

	@Override
	public void AddToProgrammersSet(Programmer... programmers) {
		repository.AddToProgrammersSet( programmers);
		
	}

	@Override
	public Set<Programmer> getProgrammersSetMembers() {
		return repository.getProgrammersSetMembers();
	}

	@Override
	public boolean isSetMember(Programmer programmer) {
		return repository.isSetMember(programmer); 
	}

	 //Hash
	
	@Override
	public void savePHash(Programmer programmer) {
		repository.saveHash(programmer);
		
	}

	@Override
	public void updatePHash(Programmer programmer) {
		repository.updateHash( programmer);
		
	}

	@Override
	public Map<Integer, Programmer> findAllPHash() {
		// TODO Auto-generated method stub
		return repository.findAllHash();
	}

	@Override
	public Programmer findPInHash(int id) {
		// TODO Auto-generated method stub
		return repository.findInHash( id);
	}

	@Override
	public void deletePhash(int id) {
		repository.deleteHash(id);
		
	}

	@Override
	public AuthToken putTokenKey(){

		AuthToken token = new AuthToken();
		token.setToken(generateToken("Sabiha"));
		token.setCreatedTime(Instant.now());
		redisTemplate.opsForValue().set("test",token);
		return (AuthToken) redisTemplate.opsForValue().get("test");
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

}
