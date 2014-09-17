package com.rd.domain;

import java.io.Serializable;

public class QuickenLoans implements Serializable {
	private static final long serialVersionUID = -7020951864957549123L;
	private Integer loansId;
	private String name;
	private String phone;
	private String area;
	private String remark;
	private String createTime;

	public Integer getLoansId() {
		return this.loansId;
	}

	public void setLoansId(Integer loansId) {
		this.loansId = loansId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}