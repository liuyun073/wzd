package com.liuyun.site.dao;

import com.liuyun.site.domain.OperationLog;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.account.OperationLogModel;

import java.util.List;

public abstract interface OperationLogDao {
	public abstract void addOperationLog(OperationLog paramOperationLog);

	public abstract int getOperationLogCount(SearchParam paramSearchParam);

	public abstract List<OperationLogModel> getOperationLogList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<OperationLogModel> getOperationLogList(SearchParam paramSearchParam);
}