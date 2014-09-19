package com.liuyun.site.domain;

import java.io.Serializable;

public class Comments implements Serializable {
	private static final long serialVersionUID = -3909317387232944253L;
	private int id;
	private int pid;
	private long user_id;
	private String module_code;
	private long article_id;
	private String comment;
	private String flag;
	private String order;
	private int status;
	private String addip;
	private String addtime;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getModule_code() {
		return this.module_code;
	}

	public void setModule_code(String module_code) {
		this.module_code = module_code;
	}

	public long getArticle_id() {
		return this.article_id;
	}

	public void setArticle_id(long article_id) {
		this.article_id = article_id;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}