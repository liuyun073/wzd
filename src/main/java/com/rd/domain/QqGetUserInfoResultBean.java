package com.rd.domain;

public class QqGetUserInfoResultBean {
	private boolean errorFlg = false;
	private String errorCode;
	private String errorMes;
	private String nickName;
	private String figureUrl;
	private String figureUrl1;
	private String figureUrl2;
	private String gender;
	private String isVip;
	private String level;

	public boolean getErrorFlg() {
		return this.errorFlg;
	}

	public void setErrorFlg(boolean errorFlg) {
		this.errorFlg = errorFlg;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMes() {
		return this.errorMes;
	}

	public void setErrorMes(String errorMes) {
		this.errorMes = errorMes;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFigureUrl() {
		return this.figureUrl;
	}

	public void setFigureUrl(String figureUrl) {
		this.figureUrl = figureUrl;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIsVip() {
		return this.isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFigureUrl1() {
		return this.figureUrl1;
	}

	public void setFigureUrl1(String figureUrl1) {
		this.figureUrl1 = figureUrl1;
	}

	public String getFigureUrl2() {
		return this.figureUrl2;
	}

	public void setFigureUrl2(String figureUrl2) {
		this.figureUrl2 = figureUrl2;
	}
}