package com.arbietDiary.arbietdiary.calendar.model.dto;

import com.arbietDiary.arbietdiary.calendar.model.FixedTimeInterface;

import lombok.Getter;

@Getter
public class FixedTimes {
	String dayId;
	String worktime;
	
	public FixedTimes() {}
	public FixedTimes(FixedTimeInterface time) {
		this.dayId = time.getDayId();
		this.worktime = time.getWorktime();
	}
}
