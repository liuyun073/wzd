package com.liuyun.site.service;

import com.liuyun.site.domain.UserLog;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
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