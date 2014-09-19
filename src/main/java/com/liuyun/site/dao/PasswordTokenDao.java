package com.liuyun.site.dao;

import com.liuyun.site.domain.PasswordToken;
import java.util.List;

public abstract interface PasswordTokenDao {
	public abstract List<PasswordToken> getAllList();

	public abstract void addPasswordToken(List<PasswordToken> paramList,
			long paramLong);

	public abstract void deletePasswordTokenById(long paramLong);

	public abstract void updatePasswordTokenByList(List<PasswordToken> paramList);

	public abstract List<PasswordToken> getPasswordTokenByUserId(long paramLong);

	public abstract String getAnswerByQuestion(String paramString,
			long paramLong);

	public abstract List<PasswordToken> getPasswordTokenByUsername(String paramString);
}