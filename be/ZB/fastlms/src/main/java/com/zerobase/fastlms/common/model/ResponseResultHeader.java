package com.zerobase.fastlms.common.model;

import lombok.Data;

@Data
public class ResponseResultHeader {
	boolean result;
	String message;
	public ResponseResultHeader(boolean result, String message) {
		// TODO Auto-generated constructor stub
		this.result = result;
		this.message = message;
	}
	public ResponseResultHeader(boolean result) {
		// TODO Auto-generated constructor stub
		this.result = result;
	}
}
