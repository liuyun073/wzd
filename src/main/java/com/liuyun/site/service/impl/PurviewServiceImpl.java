package com.liuyun.site.service.impl;

import com.liuyun.site.dao.PurviewDao;
import com.liuyun.site.service.PurviewService;
import java.util.List;

public class PurviewServiceImpl implements PurviewService {
	PurviewDao PurviewDaoImpl;

	public PurviewDao getPurviewDaoImpl() {
		return this.PurviewDaoImpl;
	}

	public void setPurviewDaoImpl(PurviewDao purviewDaoImpl) {
		this.PurviewDaoImpl = purviewDaoImpl;
	}

	public List getPurvidewById(long pid) {
		return this.PurviewDaoImpl.getPurviewByPid(pid);
	}

	public List getPurviewByUserId(long user_id) {
		return this.PurviewDaoImpl.getPurviewByUserid(user_id);
	}
}