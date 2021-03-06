package com.liuyun.site.dao;

import com.liuyun.site.domain.Areainfo;
import com.liuyun.site.domain.Linkage;
import java.util.List;

public abstract interface LinkageDao {
	public abstract List<Linkage> getLinkageByTypeid(int paramInt,
			String paramString);

	public abstract List<Areainfo> getAreainfoByPid(String paramString);

	public abstract List<Linkage> getLinkageByNid(String paramString1,
			String paramString2);

	public abstract Linkage getLinkageById(long paramLong);

	public abstract Linkage getLinkageByValue(String paramString1,
			String paramString2);

	public abstract String getLinkageNameByid(long paramLong);

	public abstract String getAreaByPid(String paramString);
}