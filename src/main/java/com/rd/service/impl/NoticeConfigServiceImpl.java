package com.rd.service.impl;

import com.rd.dao.NoticeConfigDao;
import com.rd.domain.NoticeConfig;
import com.rd.model.PageDataList;
import com.rd.service.NoticeConfigService;
import com.rd.tool.Page;
import java.util.List;
import org.apache.log4j.Logger;

public class NoticeConfigServiceImpl implements NoticeConfigService {
	private static Logger logger = Logger
			.getLogger(NoticeConfigServiceImpl.class);
	private NoticeConfigDao noticeConfigDao;

	public NoticeConfigDao getNoticeConfigDao() {
		return this.noticeConfigDao;
	}

	public void setNoticeConfigDao(NoticeConfigDao noticeConfigDao) {
		this.noticeConfigDao = noticeConfigDao;
	}

	public PageDataList noticeConfigList(int page) {
		int total = this.noticeConfigDao.getListCount();
		Page p = new Page(total, page);
		List mbList = this.noticeConfigDao.getList();
		PageDataList plist = new PageDataList(p, mbList);
		return plist;
	}

	public void add(NoticeConfig noticeConfig) {
		this.noticeConfigDao.add(noticeConfig);
	}
}