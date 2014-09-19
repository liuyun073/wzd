package com.liuyun.site.service;

import java.util.List;
import java.util.Map;

import com.liuyun.site.model.UserCreditModel;

public abstract interface AdminUserInfoService {
	
	public abstract Map<String, List<UserCreditModel>> getUserCreditList(int paramInt);
}