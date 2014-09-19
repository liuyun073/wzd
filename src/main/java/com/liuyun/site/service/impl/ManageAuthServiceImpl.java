package com.liuyun.site.service.impl;

import com.liuyun.site.dao.PurviewDao;
import com.liuyun.site.domain.Purview;
import com.liuyun.site.exception.ManageAuthException;
import com.liuyun.site.service.ManageAuthService;
import java.util.ArrayList;
import java.util.List;

public class ManageAuthServiceImpl implements ManageAuthService {
	PurviewDao purviewDao;

	public PurviewDao getPurviewDao() {
		return this.purviewDao;
	}

	public void setPurviewDao(PurviewDao purviewDao) {
		this.purviewDao = purviewDao;
	}

	public List getPurviewByPid(long pid) {
		return this.purviewDao.getPurviewByPid(pid);
	}

	public List<Purview> getPurviewByUserid(long user_id) {
		List<Purview> list = this.purviewDao.getPurviewByUserid(user_id);
		List<Purview> firstList = new ArrayList<Purview>();
		List<Purview> secList = new ArrayList<Purview>();
		List<Purview> thridList = new ArrayList<Purview>();
		for (Purview p : list) {
			if (p.getLevel() == 3) {
				Purview sec = this.purviewDao.getPurview(p.getPid());
				if (!secList.contains(sec)) {
					Purview first = this.purviewDao.getPurview(sec.getPid());
					if (!firstList.contains(first)) {
						firstList.add(first);
					}
					secList.add(sec);
				}
				thridList.add(p);
			}
		}
		thridList.addAll(firstList);
		thridList.addAll(secList);
		return thridList;
	}

	public List getAllPurview() {
		return this.purviewDao.getAllPurview();
	}

	public List getAllCheckedPurview(long user_typeid) {
		return this.purviewDao.getAllCheckedPurview(user_typeid);
	}

	public void addPurview(Purview p) {
		this.purviewDao.addPurview(p);
	}

	public Purview getPurview(long id) {
		return this.purviewDao.getPurview(id);
	}

	public void delPurview(long id) {
		boolean isRoleHasPurview = this.purviewDao.isRoleHasPurview(id);
		if (isRoleHasPurview) {
			throw new ManageAuthException("该权限还有用户组正在使用，请先删除使用中的用户组，才能删除权限！");
		}
		List list = this.purviewDao.getPurviewByPid(id);
		if ((list != null) && (list.size() > 0)) {
			throw new ManageAuthException("该权限还存在下级权限，请先删除下级权限！");
		}
		Purview p = this.purviewDao.getPurview(id);
		if (p == null) {
			throw new ManageAuthException("该权限不存在！");
		}
		this.purviewDao.delPurview(id);
	}

	public void modifyPurview(Purview p) {
		Purview purview = this.purviewDao.getPurview(p.getId());
		if (purview == null) {
			throw new ManageAuthException("该权限不存在！");
		}
		this.purviewDao.modifyPurview(p);
	}

	public void addUserTypePurviews(List purviewid, long user_type_id) {
		this.purviewDao.delUserTypePurviews(user_type_id);
		this.purviewDao.addUserTypePurviews(purviewid, user_type_id);
	}

	public void delUserTypePurviews(long user_type_id) {
		this.purviewDao.delUserTypePurviews(user_type_id);
	}
}