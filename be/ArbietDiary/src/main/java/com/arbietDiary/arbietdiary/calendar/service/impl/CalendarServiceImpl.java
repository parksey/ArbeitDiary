package com.arbietDiary.arbietdiary.calendar.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arbietDiary.arbietdiary.calendar.entity.Calendar;
import com.arbietDiary.arbietdiary.calendar.entity.Date;
import com.arbietDiary.arbietdiary.calendar.model.CalendarDateList;
import com.arbietDiary.arbietdiary.calendar.model.CalendarUserList;
import com.arbietDiary.arbietdiary.calendar.model.FixedTimeInterface;
import com.arbietDiary.arbietdiary.calendar.model.dto.CalendarDto;
import com.arbietDiary.arbietdiary.calendar.model.dto.UserLists;
import com.arbietDiary.arbietdiary.calendar.repository.CalendarRepository;
import com.arbietDiary.arbietdiary.calendar.service.CalendarService;
import com.arbietDiary.arbietdiary.calendar.service.DateService;
import com.arbietDiary.arbietdiary.member.service.WorkService;
import com.arbietDiary.arbietdiary.project.entity.Project;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CalendarServiceImpl implements CalendarService{
	private final CalendarRepository calendarRepository;
	
	private final DateService dateService;
	private final WorkService workService;
	
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
	
	@Override
	public CalendarDto addWorkTime(CalendarDto calendarDto) {
		for(UserLists user : calendarDto.getUserList()) {
			List<FixedTimeInterface> userWorkTimeList = workService.getWorkTimeWProjectId(calendarDto.getProjectId(), user.getUserId());
			user.setFixeditmes(userWorkTimeList);
		}
		return calendarDto;
	}
	
	@Override
	public CalendarDateList getCalendarDateList(Long projectId) {
		return calendarRepository.findByProject_Id(projectId);
	}
	
	@Override
	public CalendarDto getCalendarDto(CalendarUserList calendarUserList, CalendarDateList calendarDateList) {
		CalendarDto calendarDto = new CalendarDto(calendarUserList, calendarDateList);
		CalendarDto resultCalendar = addWorkTime(calendarDto);
		return resultCalendar;
	}

	@Override
	public CalendarDto getCalendarDto(CalendarUserList calendarUserList) {
		CalendarDto calendarDto = new CalendarDto(calendarUserList); 
		CalendarDto resultCalendar = addWorkTime(calendarDto);
		return resultCalendar;
	}
}
