package com.rd.service.impl;

import com.rd.dao.LinkageDao;
import com.rd.domain.Linkage;
import com.rd.service.LinkageService;
import java.util.List;

public class LinkageServiceImpl implements LinkageService {
	private LinkageDao linkageDao;

	public LinkageDao getLinkageDao() {
		return this.linkageDao;
	}

	public void setLinkageDao(LinkageDao linkageDao) {
		this.linkageDao = linkageDao;
	}

	public List<Linkage> linkageList(int typeid) {
		return this.linkageDao.getLinkageByTypeid(typeid, "");
	}
}