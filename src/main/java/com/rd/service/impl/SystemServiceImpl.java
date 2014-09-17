package com.rd.service.impl;

import com.rd.dao.SystemDao;
import com.rd.domain.SystemConfig;
import com.rd.model.SystemInfo;
import com.rd.service.SystemService;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

public class SystemServiceImpl implements SystemService {
	private Logger logger = Logger.getLogger(SystemServiceImpl.class);
	private SystemDao systemDao;

	public SystemDao getSystemDao() {
		return this.systemDao;
	}

	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}

	public SystemInfo getSystemInfo() {
		SystemInfo info = new SystemInfo();
		List<SystemConfig> list = this.systemDao.getsystem();
		for (int i = 0; i < list.size(); ++i) {
			SystemConfig sys = list.get(i);
			this.logger.debug(sys.getId() + " " + sys.getValue());
			info.addConfig(sys);
		}
		return info;
	}

	public List<SystemConfig> getSystemInfoForList() {
		return this.systemDao.getsystem();
	}

	public List<SystemConfig> getSystemInfoForListBysytle(int i) {
		return this.systemDao.getSystemListBySytle(1);
	}

	public void updateSystemInfo(List<SystemConfig> list, ServletContext context) {
		this.systemDao.updateSystemById(list);
		SystemInfo info = getSystemInfo();
		com.rd.context.Global.SYSTEMINFO = info;
		String[] webinfo = { "webname", "keywords", "description", "beian",
				"copyright", "fuwutel", "address", "weburl" };
		for (String s : webinfo) {
			this.logger.debug(s + ":" + info.getValue(s));
			context.setAttribute(s, info.getValue(s));
		}
	}

	public void clean(String url) {
	}

	public void addSystemConfig(SystemConfig systemConfig) {
		this.systemDao.addSystemConfig(systemConfig);
	}
}