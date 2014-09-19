package com.liuyun.site.service.impl;

import com.liuyun.site.dao.NoticeConfigDao;
import com.liuyun.site.domain.NoticeConfig;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.service.NoticeConfigService;
import com.liuyun.site.tool.Page;
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