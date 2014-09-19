package com.liuyun.site.model;

import com.liuyun.site.util.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

public class IdentifySearchParam extends SearchParam {
	private int real_status;
	private int phone_status;
	private int video_status;
	private int scene_status;
	private int vip_status;
	private String dotime1;
	private String dotime2;
	private Map<String, Object> map = new HashMap<String, Object>();

	public String getDotime1() {
		return this.dotime1;
	}

	public void setDotime1(String dotime1) {
		this.dotime1 = dotime1;
	}

	public String getDotime2() {
		return this.dotime2;
	}

	public void setDotime2(String dotime2) {
		this.dotime2 = dotime2;
	}

	public void setReal_status(int real_status) {
		this.real_status = real_status;
	}

	public void setPhone_status(int phone_status) {
		this.phone_status = phone_status;
	}

	public void setScene_status(int scene_status) {
		this.scene_status = scene_status;
	}

	public void setVip_status(int vip_status) {
		this.vip_status = vip_status;
	}

	public void setVideo_status(int video_status) {
		this.video_status = video_status;
	}

	public Map<String, Object> toMap() {
		if (this.real_status != 0) {
			this.map.put("status", this.real_status);
		}
		if (this.phone_status != 0) {
			this.map.put("status", this.phone_status);
		}
		if (this.scene_status != 0) {
			this.map.put("status", this.scene_status);
		}
		if (this.vip_status != 0) {
			this.map.put("status", this.vip_status);
		}
		if (this.video_status != 0) {
			this.map.put("status", this.video_status);
		}
		this.map.put("username", getUsername());
		this.map.put("realname", getRealname());
		this.map.put("email", getEmail());
		this.map.put("kefu_username", getKefu_username());
		this.map.put("dotime1", this.dotime1);
		this.map.put("dotime2", this.dotime2);
		this.map.put("qq", this.qq);
		return this.map;
	}

	public String getSearchParamSql() {
		StringBuffer sb = new StringBuffer();
		if (this.real_status != 0) {
			sb.append(" and p1.real_status=").append(this.real_status);
		}
		if (this.phone_status != 0) {
			sb.append(" and p1.phone_status=").append(this.phone_status);
		}
		if (this.scene_status != 0) {
			sb.append(" and p1.scene_status=").append(this.scene_status);
		}
		if (this.video_status != 0) {
			sb.append(" and p1.video_status=").append(this.video_status);
		}
		if (this.vip_status != 0) {
			sb.append(" and p4.vip_status=").append(this.vip_status);
		}
		if (!StringUtils.isBlank(getUsername())) {
			sb.append(" and p1.username like '%").append(getUsername()).append(
					"%'");
		}
		if (!StringUtils.isBlank(getRealname())) {
			sb.append(" and p1.realname like '%").append(getRealname()).append(
					"%'");
		}
		if (!StringUtils.isBlank(getEmail())) {
			sb.append(" and p1.email like '%").append(getEmail()).append("%'");
		}
		if (!StringUtils.isBlank(this.qq)) {
			sb.append(" and p1.qq like '%").append(getQq()).append("%'");
		}
		if (!StringUtils.isBlank(getKefu_username())) {
			sb.append(" and p4.kefu_username like '%").append(
					getKefu_username()).append("%'");
		}
		if (!StringUtils.isBlank(getTypename())) {
			sb.append(" and p8.name like '%").append(getTypename())
					.append("%'");
		}
		if (!StringUtils.isBlank(getCard_id())) {
			sb.append(" and p1.card_id like '%").append(getCard_id()).append(
					"%'");
		}
		if (!StringUtils.isBlank(getUserPhone())) {
			sb.append(" and p1.phone like '%").append(getUserPhone()).append(
					"%'");
		}
		if (!StringUtils.isBlank(getPhone())) {
			sb.append(" and p1.phone like '%").append(getPhone()).append("%'");
		}
		String dotimeStr1 = null;
		String dotimeStr2 = null;
		try {
			dotimeStr1 = Long.toString(DateUtils.valueOf(this.dotime1)
					.getTime() / 1000L);
		} catch (Exception e) {
			dotimeStr1 = "";
		}
		try {
			Date d = DateUtils.valueOf(this.dotime2);
			d = DateUtils.rollDay(d, 1);
			dotimeStr2 = Long.toString(d.getTime() / 1000L);
		} catch (Exception e) {
			dotimeStr2 = "";
		}
		if (!StringUtils.isBlank(dotimeStr1)) {
			sb.append(" and p1.addtime>" + dotimeStr1 + " ");
		}
		if (!StringUtils.isBlank(dotimeStr2)) {
			sb.append(" and p1.addtime<" + dotimeStr2 + " ");
		}
		return sb.toString();
	}
}