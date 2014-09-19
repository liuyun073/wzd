package com.liuyun.site.common.enums;

public enum EnumCooperationLoginType {
	COOPERATION_QQ((byte)1), COOPERATION_SINA((byte)2);

	private byte value;

	private EnumCooperationLoginType(byte value) {
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

	public boolean equals(EnumCooperationLoginType value) {
		return (value != null) && (this.value == value.getValue());
	}
}