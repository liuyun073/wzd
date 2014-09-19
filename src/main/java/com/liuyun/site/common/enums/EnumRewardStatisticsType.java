package com.liuyun.site.common.enums;

public enum EnumRewardStatisticsType {
	
	RECHARGE_AWARD((byte)1), 
	INVITE_AWARD((byte)2), 
	INVITER_AWARD((byte)3), 
	TENDER_AWARD((byte)4), 
	HUIKUAN_AWARD((byte)5);

	private byte value;

	private EnumRewardStatisticsType(byte value) {
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

	public boolean equals(EnumRewardStatisticsType value) {
		return (value != null) && (this.value == value.getValue());
	}
}