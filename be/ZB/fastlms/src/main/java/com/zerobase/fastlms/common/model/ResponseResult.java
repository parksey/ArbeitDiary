package com.zerobase.fastlms.common.model;

import lombok.Data;

@Data
public class ResponseResult {
	ResponseResultHeader header;
	Object body;
	
	public ResponseResult(boolean result, String message) {
		// TODO Auto-generated constructor stub
		header = new ResponseResultHeader(result, message);
	}

	public ResponseResult(boolean result) {
		// TODO Auto-generated constructor stub
		header = new ResponseResultHeader(result);
	}
}
