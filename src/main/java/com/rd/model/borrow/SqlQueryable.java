package com.rd.model.borrow;

import com.rd.tool.Page;

public abstract interface SqlQueryable {
	public abstract String getTypeSql();

	public abstract String getStatusSql();

	public abstract String getOrderSql();

	public abstract String getSearchParamSql();

	public abstract String getLimitSql();

	public abstract String getPageStr(Page paramPage);

	public abstract String getSerachStr();

	public abstract String getOrderStr();
}