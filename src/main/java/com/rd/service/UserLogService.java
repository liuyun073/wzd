package com.rd.service;

import com.rd.domain.UserLog;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface UserLogService {
	public abstract void addLog(UserLog paramUserLog);

	public abstract int getLogCountByUserId(long paramLong,
			SearchParam paramSearchParam);

	public abstract List getLogListByUserId(long paramLong, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int getLogCountByParam(SearchParam paramSearchParam);

	public abstract List getLogListByParams(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract PageDataList getUserLogList(int paramInt,
			SearchParam paramSearchParam);
}