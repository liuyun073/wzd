package com.liuyun.site.service.impl;

import com.liuyun.site.dao.CooperationLoginDao;
import com.liuyun.site.domain.CooperationLogin;
import com.liuyun.site.service.CooperationLoginService;

public class CooperationLoginServiceImpl implements CooperationLoginService {
	private CooperationLoginDao cooperationLoginDao;

	public void addCooperationLogin(CooperationLogin cooperation) {
		CooperationLogin cooperationLogin = this.cooperationLoginDao
				.getCooperationLogin(cooperation.getOpen_id(), cooperation
						.getOpen_key(), cooperation.getType());
		if (cooperationLogin == null)
			this.cooperationLoginDao.addCooperationLogin(cooperation);
	}

	public CooperationLogin getCooperationLogin(String open_id,
			String opend_key, Byte type) {
		return this.cooperationLoginDao.getCooperationLogin(open_id, opend_key,
				type);
	}

	public CooperationLogin getCooperationLoginByUserId(long user_id, Byte type) {
		return this.cooperationLoginDao.getCooperationLoginByUserId(user_id,
				type);
	}

	public void updateCooperationUserId(long user_id, String open_id,
			String opend_key, Byte type) {
		this.cooperationLoginDao.updateCooperationUserId(user_id, open_id,
				opend_key, type);
	}

	public void updateCooperationUserIdById(long user_id, long id) {
		this.cooperationLoginDao.updateCooperationUserIdById(user_id, id);
	}

	public CooperationLoginDao getCooperationLoginDao() {
		return this.cooperationLoginDao;
	}

	public void setCooperationLoginDao(CooperationLoginDao cooperationLoginDao) {
		this.cooperationLoginDao = cooperationLoginDao;
	}
}