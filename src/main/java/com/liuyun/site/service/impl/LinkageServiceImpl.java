package com.liuyun.site.service.impl;

import com.liuyun.site.dao.LinkageDao;
import com.liuyun.site.domain.Linkage;
import com.liuyun.site.service.LinkageService;
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