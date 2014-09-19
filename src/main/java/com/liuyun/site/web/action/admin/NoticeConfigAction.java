package com.liuyun.site.web.action.admin;

import com.liuyun.site.domain.NoticeConfig;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.LinkageService;
import com.liuyun.site.service.NoticeConfigService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.util.List;

public class NoticeConfigAction extends BaseAction {
	private NoticeConfigService noticeConfigService;
	private NoticeConfig noticeConfig;
	private LinkageService linkageService;

	public LinkageService getLinkageService() {
		return this.linkageService;
	}

	public void setLinkageService(LinkageService linkageService) {
		this.linkageService = linkageService;
	}

	public NoticeConfig getNoticeConfig() {
		return this.noticeConfig;
	}

	public void setNoticeConfig(NoticeConfig noticeConfig) {
		this.noticeConfig = noticeConfig;
	}

	public NoticeConfigService getNoticeConfigService() {
		return this.noticeConfigService;
	}

	public void setNoticeConfigService(NoticeConfigService noticeConfigService) {
		this.noticeConfigService = noticeConfigService;
	}

	public String index() {
		return SUCCESS;
	}

	public String update() {
		return SUCCESS;
	}

	public String clean() {
		System.out.println(this.context.getRealPath(""));
		return SUCCESS;
	}

	public String noticeConfigList() {
		int page = NumberUtils.getInt(StringUtils.isNull(this.request
				.getParameter("page")));
		PageDataList pList = this.noticeConfigService.noticeConfigList(page);
		setPageAttribute(pList, new SearchParam());
		return SUCCESS;
	}

	public String addNoticeConfig() {
		List linkageList = this.linkageService.linkageList(45);
		this.request.setAttribute("linkageList", linkageList);
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (!StringUtils.isBlank(actionType)) {
			this.noticeConfig = new NoticeConfig();
			String type = StringUtils.isNull(this.request.getParameter("type"));
			long sms = NumberUtils.getLong(StringUtils.isNull(this.request
					.getParameter("sms")));
			long email = NumberUtils.getLong(StringUtils.isNull(this.request
					.getParameter("email")));
			long letters = NumberUtils.getLong(StringUtils.isNull(this.request
					.getParameter("letters")));
			this.noticeConfig.setEmail(email);
			this.noticeConfig.setLetters(letters);
			this.noticeConfig.setSms(sms);
			this.noticeConfig.setType(type);
			this.noticeConfigService.add(this.noticeConfig);
			message("新增通知配置成功！", "/admin/notice/noticeConfigList.html");
			return ADMINMSG;
		}
		return SUCCESS;
	}
}