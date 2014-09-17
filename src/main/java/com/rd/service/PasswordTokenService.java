package com.rd.service;

import com.rd.domain.PasswordToken;
import java.util.List;

public abstract interface PasswordTokenService {
	public abstract void updatePasswordTokenByList(List<PasswordToken> paramList);

	public abstract void addPasswordToken(List<PasswordToken> paramList,
			long paramLong);

	public abstract void deletePasswordTokenById(long paramLong);

	public abstract List<PasswordToken> getPasswordToken();

	public abstract List<PasswordToken> getPasswordTokenByUserId(long paramLong);

	public abstract String getAnswerByQuestion(String paramString,
			long paramLong);

	public abstract List getPasswordTokenByUsername(String paramString);
}