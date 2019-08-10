package com.jjunpro.koreanair.account.enums;

// Enum DTO
public class EnumValue {

	private String Key;
	private String Value;
	
	public EnumValue(EnumModel enumModel) {
		Key = enumModel.getKey();
		Value = enumModel.getValue();
	}

	public final String getKey() {
		return Key;
	}

	public final String getValue() {
		return Value;
	}

}
