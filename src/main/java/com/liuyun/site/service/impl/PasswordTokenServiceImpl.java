package com.liuyun.site.service.impl;

import com.liuyun.site.dao.PasswordTokenDao;
import com.liuyun.site.domain.PasswordToken;
import com.liuyun.site.service.PasswordTokenService;
import java.util.List;

public class PasswordTokenServiceImpl implements PasswordTokenService {
	private PasswordTokenDao passwordTokenDao;

	public PasswordTokenDao getPasswordTokenDao() {
		return this.passwordTokenDao;
	}

	public void setPasswordTokenDao(PasswordTokenDao passwordTokenDao) {
		this.passwordTokenDao = passwordTokenDao;
	}

	public void addPasswordToken(List<PasswordToken> tokenList, long userId) {
		this.passwordTokenDao.addPasswordToken(tokenList, userId);
	}

	public void deletePasswordTokenById(long id) {
		this.passwordTokenDao.deletePasswordTokenById(id);
	}

	public List<PasswordToken> getPasswordToken() {
		return this.passwordTokenDao.getAllList();
	}

	public void updatePasswordTokenByList(List<PasswordToken> list) {
		this.passwordTokenDao.updatePasswordTokenByList(list);
	}

	public List getPasswordTokenByUserId(long userId) {
		return this.passwordTokenDao.getPasswordTokenByUserId(userId);
	}

	public String getAnswerByQuestion(String question, long userId) {
		return this.passwordTokenDao.getAnswerByQuestion(question, userId);
	}

	public List getPasswordTokenByUsername(String username) {
		return this.passwordTokenDao.getPasswordTokenByUsername(username);
	}
}