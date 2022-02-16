package com.arbietDiary.arbietdiary.error;

public class AuthenticationUserInputException extends RuntimeException{
	public AuthenticationUserInputException(String error) {
		super(error);
	}
}
