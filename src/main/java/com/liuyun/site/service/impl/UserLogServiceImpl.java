package com.liuyun.site.service.impl;

import com.liuyun.site.dao.UserLogDao;
import com.liuyun.site.domain.UserLog;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.UserLogService;
import com.liuyun.site.tool.Page;
import java.util.List;

public class UserLogServiceImpl implements UserLogService {
	private UserLogDao userLogDao;

	public UserLogDao getUserLogDao() {
		return this.userLogDao;
	}

	public void setUserLogDao(UserLogDao userLogDao) {
		this.userLogDao = userLogDao;
	}

	public void addLog(UserLog userLog) {
		this.userLogDao.addUserLog(userLog);
	}

	public int getLogCountByUserId(long userId, SearchParam param) {
		return this.userLogDao.getLogCountByUserId(userId, param);
	}

	public List getLogListByUserId(long userId, int start, int end,
			SearchParam param) {
		return this.userLogDao.getLogListByUserId(userId, start, end, param);
	}

	public int getLogCountByParam(SearchParam param) {
		return this.userLogDao.getLogCountByParam(param);
	}

	public List getLogListByParams(int start, int end, SearchParam param) {
		return this.userLogDao.getLogListByParams(start, end, param);
	}

	public PageDataList getUserLogList(int currentPage, SearchParam param) {
		int total = this.userLogDao.getLogCountByParam(param);
		Page p = new Page(total, currentPage);
		List list = this.userLogDao.getLogListByParams(p.getStart(), p
				.getPernum(), param);

		return new PageDataList(p, list);
	}
}