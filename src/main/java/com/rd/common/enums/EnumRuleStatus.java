package com.rd.common.enums;

public enum EnumRuleStatus {
	RULE_STATUS_YES((byte)1), RULE_STATUS_NO((byte)2);

	private byte value;

	private EnumRuleStatus(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return this.value;
	}

	public boolean equals(byte value) {
		return this.value == value;
	}

	public boolean equals(Byte value) {
		return (value != null) && (this.value == value.byteValue());
	}

	public boolean equals(EnumRuleStatus value) {
		return (value != null) && (this.value == value.getValue());
	}
}