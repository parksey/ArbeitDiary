package com.arbietDiary.arbietdiary.calendar.model.type;

public enum DayEnum {
	MONDAY(0),
	TUESDAY(1),
	WEDNESDAY(1),
	THURSDAY(3),
	FRIDAY(4),
	SATURDAY(5),
	SUNDAY(6);

	private final int value;
	DayEnum(int value) {
		this.value = value;
		// TODO Auto-generated constructor stub
	}
	
	public int getValue() {
		return value;
	}
}
