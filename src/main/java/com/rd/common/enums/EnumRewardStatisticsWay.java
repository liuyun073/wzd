package com.rd.common.enums;

public enum EnumRewardStatisticsWay {
	MONEY((byte)1), PERCENT((byte)2);

	private byte value;

	private EnumRewardStatisticsWay(byte value) {
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

	public boolean equals(EnumRewardStatisticsWay value) {
		return (value != null) && (this.value == value.getValue());
	}
}