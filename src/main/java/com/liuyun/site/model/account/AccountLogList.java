package com.liuyun.site.model.account;

import com.liuyun.site.tool.Page;
import java.util.List;

public class AccountLogList {
	private Page page;
	private List<AccountLogModel> list;

	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<AccountLogModel> getList() {
		return this.list;
	}

	public void setList(List<AccountLogModel> list) {
		this.list = list;
	}
}