package com.rd.service;

import com.rd.domain.SystemConfig;
import com.rd.model.SystemInfo;
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