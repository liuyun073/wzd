package com.liuyun.site.dao;

import com.liuyun.site.domain.UserLog;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserLogModel;

import java.util.List;

public abstract interface UserLogDao {
	public abstract void addUserLog(UserLog paramUserLog);

	public abstract int getLogCountByUserId(long paramLong,
			SearchParam paramSearchParam);

	public abstract List<UserLogModel> getLogListByUserId(long paramLong, int paramInt1,
			int paramInt2, SearchParam paramSearchParam);

	public abstract int getLogCountByParam(SearchParam paramSearchParam);

	public abstract List<UserLogModel> getLogListByParams(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);
}