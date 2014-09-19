package com.liuyun.site.service.impl;

import com.liuyun.site.dao.UserCreditDao;
import com.liuyun.site.model.UserCreditModel;
import com.liuyun.site.service.AdminUserInfoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUserInfoSericeImpl implements AdminUserInfoService {
	private UserCreditDao usercreditdao;

	public Map<String, List<UserCreditModel>> getUserCreditList(int Page) {
		Map<String, List<UserCreditModel>> map = new HashMap<String, List<UserCreditModel>>();
		map.put("List", this.usercreditdao.getUserCreditByPageNumber(Page, 10));
		return map;
	}

	public UserCreditDao getUsercreditdao() {
		return this.usercreditdao;
	}

	public void setUsercreditdao(UserCreditDao usercreditdao) {
		this.usercreditdao = usercreditdao;
	}
}