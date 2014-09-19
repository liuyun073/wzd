package com.liuyun.site.service;

import com.liuyun.site.domain.Purview;
import java.util.List;

public abstract interface ManageAuthService {
	public abstract List getPurviewByPid(long paramLong);

	public abstract List<Purview> getPurviewByUserid(long paramLong);

	public abstract List getAllPurview();

	public abstract List getAllCheckedPurview(long paramLong);

	public abstract void addPurview(Purview paramPurview);

	public abstract Purview getPurview(long paramLong);

	public abstract void delPurview(long paramLong);

	public abstract void modifyPurview(Purview paramPurview);

	public abstract void addUserTypePurviews(List paramList, long paramLong);

	public abstract void delUserTypePurviews(long paramLong);
}