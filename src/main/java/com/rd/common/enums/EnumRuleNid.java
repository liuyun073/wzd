package com.rd.common.enums;

public enum EnumRuleNid {
	PERCENT_AWARD("percent_award"), INVITE_AWARD("invite_award"), INVITER_AWARD(
			"inviter_award"), TENDER_AWARD("tender_award"), INTEGRAL_TENDER(
			"integral_tender");

	private String value;

	private EnumRuleNid(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public boolean equals(String value) {
		return (value != null) && (this.value == value);
	}

	public boolean equals(EnumRuleNid value) {
		return (value != null) && (this.value == value.getValue());
	}
}