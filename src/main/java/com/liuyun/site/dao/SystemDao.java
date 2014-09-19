package com.liuyun.site.dao;

import com.liuyun.site.domain.SystemConfig;
import java.util.List;

public abstract interface SystemDao {
	public abstract List<SystemConfig> getsystem();

	public abstract void updateSystemById(List<SystemConfig> paramList);

	public abstract List<SystemConfig> getSystemListBySytle(int paramInt);

	public abstract void addSystemConfig(SystemConfig paramSystemConfig);

	public abstract List<String> getAllowIp();
}