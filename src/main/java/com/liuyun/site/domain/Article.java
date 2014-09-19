package com.liuyun.site.domain;

import java.io.Serializable;

public class Article implements Serializable {
	private static final long serialVersionUID = 7029710509016395903L;
	private long id;
	private long site_id;
	private String name;
	private String littitle;
	private int status;
	private String flag;
	private String source;
	private String publish;
	private String author;
	private String summary;
	private String content;
	private int order;
	private int hits;
	private String litpic;
	private long user_id;
	private String addtime;
	private String addip;
	private String sitename;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSite_id() {
		return this.site_id;
	}

	public void setSite_id(long siteId) {
		this.site_id = siteId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLittitle() {
		return this.littitle;
	}

	public void setLittitle(String littitle) {
		this.littitle = littitle;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPublish() {
		return this.publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getHits() {
		return this.hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getLitpic() {
		return this.litpic;
	}

	public void setLitpic(String litpic) {
		this.litpic = litpic;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long userId) {
		this.user_id = userId;
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

	public String getSitename() {
		return this.sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
}