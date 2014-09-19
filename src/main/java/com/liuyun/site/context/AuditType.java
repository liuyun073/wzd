package com.liuyun.site.context;

import com.liuyun.site.util.StringUtils;

public enum AuditType {
	realnameAudit, emailAudit, phoneAudit, videoAudit, sceneAudit;

	public static AuditType getAuditType(String name) {
		name = StringUtils.isNull(name);
		if (name.equals("realname"))
			return realnameAudit;
		if (name.equals("email"))
			return emailAudit;
		if (name.equals("phone"))
			return phoneAudit;
		if (name.equals("video"))
			return videoAudit;
		if (name.equals("scene")) {
			return sceneAudit;
		}
		return null;
	}
}