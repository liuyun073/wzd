package com.rd.service.impl;

import com.rd.dao.UserLogDao;
import com.rd.domain.UserLog;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.service.UserLogService;
import com.rd.tool.Page;
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