package com.rd.model;

import com.rd.domain.HongBao;
import java.io.Serializable;

public class HongBaoModel extends HongBao implements Serializable {
	private static final long serialVersionUID = 6026604036412862478L;
	private String username;
	private String typename;
	private String hongbao;

	public String getHongbao() {
		return this.hongbao;
	}

	public void setHongbao(String hongbao) {
		this.hongbao = hongbao;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}