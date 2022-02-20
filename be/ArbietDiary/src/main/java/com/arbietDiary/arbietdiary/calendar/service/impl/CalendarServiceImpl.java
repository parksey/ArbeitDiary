package com.arbietDiary.arbietdiary.calendar.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.arbietDiary.arbietdiary.calendar.entity.Calendar;
import com.arbietDiary.arbietdiary.calendar.entity.Date;
import com.arbietDiary.arbietdiary.calendar.repository.CalendarRepository;
import com.arbietDiary.arbietdiary.calendar.service.CalendarService;
import com.arbietDiary.arbietdiary.calendar.service.DateService;
import com.arbietDiary.arbietdiary.project.entity.Project;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CalendarServiceImpl implements CalendarService{
	private final CalendarRepository calendarRepository;
	
	private final DateService dateService;
	
	@Override
	public boolean makeCalendar(Project project) {
		boolean isExist = calendarRepository.existsByProject_Id(project.getId());
		if(isExist) {
			System.out.println("[캘린더 생성] : 실패");
			return false;
		}
		
		Calendar calendar = Calendar.builder()
				.project(project)
				.build();
		
		calendarRepository.save(calendar);
		
		List<Date> dateList = new ArrayList<Date>();
		dateService.makeCalendarDate(calendar);
		return true;
	}
	
	@Override
	public boolean makeDaysAllCalendar() {
		List<Calendar> calendars = calendarRepository.findAll();
		if(calendars.isEmpty()) {
			System.out.println("[주기적 날짜 생성] : 실패");
			return false;
		}
		dateService.makeDays(calendars);
		
		return true;
	}
}
