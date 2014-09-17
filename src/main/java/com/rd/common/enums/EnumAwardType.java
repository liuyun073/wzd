package com.rd.common.enums;

public enum EnumAwardType {
	AWARD_TYPE_POINT(1),

	AWARD_TYPE_TIMES(2),

	AWARD_TYPE_RATIO(3);

	private int value;

	private EnumAwardType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public boolean equals(int value) {
		return this.value == value;
	}

	public boolean equals(EnumAwardErrorType value) {
		return (value != null) && (this.value == value.getValue());
	}
}