package com.rd.model;

import com.rd.model.borrow.BorrowModel;
import java.io.Serializable;

public class UserBorrowModel extends BorrowModel implements Serializable {
	private static final long serialVersionUID = 6956108421611997455L;
	private int isqiye;
	private long fastid;
	private String username;
	private String user_area;
	private String kefu_username;
	private String qq;
	private int credit_jifen;
	private String credit_pic;
	private String add_area;
	private double scales;
	private double Surplus;
	private String usetypename;
	private String realname;

	public double getSurplus() {
		return this.Surplus;
	}

	public void setSurplus(double surplus) {
		this.Surplus = surplus;
	}

	public int getIsqiye() {
		return this.isqiye;
	}

	public void setIsqiye(int isqiye) {
		this.isqiye = isqiye;
	}

	public long getFastid() {
		return this.fastid;
	}

	public void setFastid(long fastid) {
		this.fastid = fastid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getArea() {
		return this.user_area;
	}

	public void setUser_area(String user_area) {
		this.user_area = user_area;
	}

	public String getKefu_username() {
		return this.kefu_username;
	}

	public void setKefu_username(String kefu_username) {
		this.kefu_username = kefu_username;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int getCredit_jifen() {
		return this.credit_jifen;
	}

	public void setCredit_jifen(int credit_jifen) {
		this.credit_jifen = credit_jifen;
	}

	public String getCredit_pic() {
		return this.credit_pic;
	}

	public void setCredit_pic(String credit_pic) {
		this.credit_pic = credit_pic;
	}

	public String getAdd_area() {
		return this.add_area;
	}

	public void setAdd_area(String add_area) {
		this.add_area = add_area;
	}

	public double getScales() {
		return this.scales;
	}

	public void setScales(double scales) {
		this.scales = scales;
	}

	public String getUsetypename() {
		return this.usetypename;
	}

	public void setUsetypename(String usetypename) {
		this.usetypename = usetypename;
	}

	public String getUser_area() {
		return this.user_area;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
}