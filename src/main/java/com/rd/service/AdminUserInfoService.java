package com.rd.service;

import java.util.List;
import java.util.Map;

import com.rd.model.UserCreditModel;

public abstract interface AdminUserInfoService {
	
	public abstract Map<String, List<UserCreditModel>> getUserCreditList(int paramInt);
}