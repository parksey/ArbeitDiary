package com.arbietDiary.arbietdiary.calendar.model;

import org.springframework.beans.factory.annotation.Value;

public interface FixedTimeInterface {
	@Value("#{target.workDay}")
	String getDayId();
	
	@Value("#{target.workTime}")
	String getWorktime();
}
