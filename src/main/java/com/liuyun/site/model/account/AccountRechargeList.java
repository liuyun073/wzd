package com.liuyun.site.model.account;

import com.liuyun.site.domain.AccountRecharge;
import com.liuyun.site.tool.Page;
import java.util.List;

public class AccountRechargeList {
	private List<AccountRecharge> list;
	private Page page;

	public List<AccountRecharge> getList() {
		return this.list;
	}

	public void setList(List<AccountRecharge> list) {
		this.list = list;
	}

	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}