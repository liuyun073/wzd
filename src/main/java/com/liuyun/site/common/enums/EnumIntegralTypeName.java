package com.liuyun.site.common.enums;

public enum EnumIntegralTypeName {
	INTEGRAL_EMAIL("email"),

	INTEGRAL_PHONE("phone"),

	INTEGRAL_VIDEO("video"),

	INTEGRAL_REAL_NAME("realname"),

	INTEGRAL_SCENE("scene"),

	INTEGRAL_CERTIFICATE("zhengjian"),

	INTEGRAL_INVEST("invest_success"),

	INTEGRAL_BORROW("borrow_success"),

	INTEGRAL_CONVERT("integral_convert"),

	INTEGRAL_AWARD("integral_award");

	private String value;

	private EnumIntegralTypeName(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public boolean equals(String value) {
		return this.value.equals(value);
	}

	public boolean equals(Byte value) {
		return (value != null) && (this.value.equals(value));
	}

	public boolean equals(EnumIntegralTypeName value) {
		return (value != null) && (this.value == value.getValue());
	}
}