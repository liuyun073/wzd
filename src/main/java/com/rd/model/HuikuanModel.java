package com.rd.model;

import com.rd.domain.Huikuan;
import java.io.Serializable;

public class HuikuanModel extends Huikuan implements Serializable {
	private static final long serialVersionUID = 6026604036412862478L;
	private String username;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}