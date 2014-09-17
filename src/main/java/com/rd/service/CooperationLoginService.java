package com.rd.service;

import com.rd.domain.CooperationLogin;

public abstract interface CooperationLoginService {
	public abstract void addCooperationLogin(
			CooperationLogin paramCooperationLogin);

	public abstract CooperationLogin getCooperationLogin(String paramString1,
			String paramString2, Byte paramByte);

	public abstract CooperationLogin getCooperationLoginByUserId(
			long paramLong, Byte paramByte);

	public abstract void updateCooperationUserId(long paramLong,
			String paramString1, String paramString2, Byte paramByte);

	public abstract void updateCooperationUserIdById(long paramLong1,
			long paramLong2);
}