package com.rd.common.enums;

public enum EnumRuleBorrowCategory {
	
	NO_BORROW_CATEGORY((byte)-1), 
	BORROW_MONTH((byte)0), 
	BORROW_DAY((byte)1);

	private byte value;

	private EnumRuleBorrowCategory(byte value) {
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

	public boolean equals(EnumRuleBorrowCategory value) {
		return (value != null) && (this.value == value.getValue());
	}
}