package com.rd.common.enums;

public enum EnumAwardErrorType {
	
	RESULT_NO_REGISTER((byte)1),

	RESULT_PARAMETER_ERROR((byte)2),

	RESULT_INVALID_RULE_ID((byte)3),

	RESULT_BEFORE_START_TIME((byte)4),

	RESULT_AFTER_END_TIME((byte)5),

	RESULT_POINT_LIMIT((byte)6),

	RESULT_TIME_LIMIT((byte)7),

	RESULT_NO_AWARD((byte)8),

	RESULT_NO_AWARD_OBJ((byte)9),

	RESULT_MONEY_LIMIT((byte)10);

	private byte value;

	private EnumAwardErrorType(byte value) {
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

	public boolean equals(EnumAwardErrorType value) {
		return (value != null) && (this.value == value.getValue());
	}
}