package com.rd.domain;

import java.io.Serializable;

public class PasswordToken implements Serializable {
	private static final long serialVersionUID = -4978559276371568191L;
	private long id;
	private long user_id;
	private String question;
	private String answer;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}