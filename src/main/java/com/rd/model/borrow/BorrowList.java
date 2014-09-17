package com.rd.model.borrow;

import com.rd.tool.Page;
import java.util.List;

public class BorrowList {
	private List<?> list;
	private Page page;

	public List<?> getList() {
		return this.list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}