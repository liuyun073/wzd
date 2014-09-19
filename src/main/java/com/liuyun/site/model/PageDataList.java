package com.liuyun.site.model;

import com.liuyun.site.tool.Page;
import java.util.List;

public class PageDataList {
	private Page page;
	private List<?> list;
	private int type;

	public PageDataList() {
	}

	public PageDataList(Page page, List<?> list) {
		this.page = page;
		this.list = list;
	}

	public PageDataList(Page page, List<?> list, int type) {
		this.page = page;
		this.list = list;
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<?> getList() {
		return this.list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}