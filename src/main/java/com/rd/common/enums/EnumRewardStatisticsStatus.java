package com.rd.common.enums;

public enum EnumRewardStatisticsStatus {
	CASH_UN_BACK((byte)1), 
	CASH_BACK((byte)2), 
	VERIFY_UN_PASS((byte)3), 
	CASH_BACK_FAIL((byte)4), 
	NO_USE((byte)5);

	private byte value;

	private EnumRewardStatisticsStatus(byte value) {
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

	public boolean equals(EnumRewardStatisticsStatus value) {
		return (value != null) && (this.value == value.getValue());
	}
}