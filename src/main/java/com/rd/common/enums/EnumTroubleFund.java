package com.rd.common.enums;

public enum EnumTroubleFund {
	
	FIRST((byte)1), ZERO((byte)0), SECOND((byte)2);

	private byte value;

	private EnumTroubleFund(byte value) {
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

	public boolean equals(EnumTroubleFund value) {
		return (value != null) && (this.value == value.getValue());
	}
}