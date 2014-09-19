package com.liuyun.site.model.award;

import com.liuyun.site.common.enums.EnumAwardErrorType;
import java.io.Serializable;

public class AwardResult implements Serializable {
	private static final long serialVersionUID = 1708885841467808090L;
	private String is_success = "F";
	private EnumAwardErrorType error;
	private String level_no;
	private String name;
	private String money;
	private String times;

	public String getIs_success() {
		return this.is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public EnumAwardErrorType getError() {
		return this.error;
	}

	public void setError(EnumAwardErrorType error) {
		this.error = error;
	}

	public String getLevel_no() {
		return this.level_no;
	}

	public void setLevel_no(String level_no) {
		this.level_no = level_no;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTimes() {
		return this.times;
	}

	public void setTimes(String times) {
		this.times = times;
	}
}