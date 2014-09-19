package com.liuyun.site.dao;

import com.liuyun.site.domain.Purview;
import java.util.List;

public abstract interface PurviewDao {
	public abstract List<Purview> getPurviewByPid(long paramLong);

	public abstract List<Purview> getPurviewByUserid(long paramLong);

	public abstract List<Purview> getAllPurview();

	public abstract List<Purview> getAllCheckedPurview(long paramLong);

	public abstract void addPurview(Purview paramPurview);

	public abstract Purview getPurview(long paramLong);

	public abstract void delPurview(long paramLong);

	public abstract boolean isRoleHasPurview(long paramLong);

	public abstract void modifyPurview(Purview paramPurview);

	public abstract void addUserTypePurviews(List<Integer> paramList,
			long paramLong);

	public abstract void delUserTypePurviews(long paramLong);
}