package com.arbietDiary.arbietdiary.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.arbietDiary.arbietdiary.calendar.service.CalendarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Scheduler {
	private final CalendarService calendarService;
	
	@Scheduled(cron = "59 53 23 * * *")
	public void makeAllDate() {
		calendarService.makeDaysAllCalendar();
	}
}
