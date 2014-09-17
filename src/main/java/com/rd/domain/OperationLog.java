package com.rd.domain;

import java.io.Serializable;

public class OperationLog implements Serializable {
	private static final long serialVersionUID = 7029710509016395903L;
	private long id;
	private long user_id;
	private long verify_user;
	private String type;
	private String addtime;
	private String addip;
	private String operationResult;
	private String payment;

	public OperationLog() {
	}

	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public OperationLog(long user_id, long verify_user, String type,
			String addtime, String addip, String operationResult) {
		this.user_id = user_id;
		this.verify_user = verify_user;
		this.type = type;
		this.addtime = addtime;
		this.addip = addip;
		this.operationResult = operationResult;
	}

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

	public long getVerify_user() {
		return this.verify_user;
	}

	public void setVerify_user(long verify_user) {
		this.verify_user = verify_user;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public String getOperationResult() {
		return this.operationResult;
	}

	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}
}