package com.liuyun.site.service;

import com.liuyun.site.domain.SystemConfig;
import com.liuyun.site.model.SystemInfo;
import java.util.List;
import javax.servlet.ServletContext;

public abstract interface SystemService {
	public abstract SystemInfo getSystemInfo();

	public abstract List<SystemConfig> getSystemInfoForList();

	public abstract List<SystemConfig> getSystemInfoForListBysytle(int paramInt);

	public abstract void updateSystemInfo(List<SystemConfig> paramList, ServletContext paramServletContext);

	public abstract void clean(String paramString);

	public abstract void addSystemConfig(SystemConfig paramSystemConfig);
}