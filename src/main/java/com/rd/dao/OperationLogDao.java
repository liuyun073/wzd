package com.rd.dao;

import com.rd.domain.OperationLog;
import com.rd.model.SearchParam;
import com.rd.model.account.OperationLogModel;

import java.util.List;

public abstract interface OperationLogDao {
	public abstract void addOperationLog(OperationLog paramOperationLog);

	public abstract int getOperationLogCount(SearchParam paramSearchParam);

	public abstract List<OperationLogModel> getOperationLogList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<OperationLogModel> getOperationLogList(SearchParam paramSearchParam);
}