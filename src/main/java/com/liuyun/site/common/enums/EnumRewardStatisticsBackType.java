package com.liuyun.site.common.enums;

public enum EnumRewardStatisticsBackType {
	TIME_BACK((byte)1), AUTO_BACK((byte)2), ARTIFICIAL_BACK((byte)3);

	private byte value;

	private EnumRewardStatisticsBackType(byte value) {
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

	public boolean equals(EnumRewardStatisticsBackType value) {
		return (value != null) && (this.value == value.getValue());
	}
}