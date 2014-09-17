package com.rd.dao;

import com.rd.domain.UserLog;
import com.rd.model.SearchParam;
import com.rd.model.UserLogModel;

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