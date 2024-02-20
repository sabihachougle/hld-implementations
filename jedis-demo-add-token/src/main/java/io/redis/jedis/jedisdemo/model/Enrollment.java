package io.redis.jedis.jedisdemo.model;

import java.io.Serializable;

public class Enrollment implements Serializable{
	
	private String month;
	private String score;
	private String name;

	public Enrollment(){

	}
	public Enrollment(String month, String score, String name) {
		this.month = month;
		this.score = score;
		this.name = name;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Enrollment{" +
				"month=" + month +
				", score='" + score + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
