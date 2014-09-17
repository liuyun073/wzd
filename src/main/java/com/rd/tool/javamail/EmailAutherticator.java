package com.rd.tool.javamail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailAutherticator extends Authenticator {
	String username = "welkinrook@163.com";

	String password = "76712144";

	public EmailAutherticator() {
	}

	public EmailAutherticator(String user, String pwd) {
		this.username = user;
		this.password = pwd;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.username, this.password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}