package com.liuyun.site.common.enums;

public enum EnumIntegralCategory {
	INTEGRAL_TENDER((byte)1),

	INTEGRAL_BORROW((byte)2),

	INTEGRAL_GIFT((byte)3),

	INTEGRAL_EXPENSE((byte)4),

	INTEGRAL_VALID((byte)5),

	INTEGRAL_VALUE((byte)6);

	private byte value;

	private EnumIntegralCategory(byte value) {
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

	public boolean equals(EnumIntegralCategory value) {
		return (value != null) && (this.value == value.getValue());
	}
}