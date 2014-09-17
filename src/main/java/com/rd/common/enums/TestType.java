package com.rd.common.enums;

public enum TestType {
	
	DD(0), RR(1);
	
	
	private int value;

	private TestType(int value) {
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
