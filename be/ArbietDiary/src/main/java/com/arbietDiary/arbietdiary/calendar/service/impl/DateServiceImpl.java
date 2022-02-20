package com.arbietDiary.arbietdiary.calendar.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.arbietDiary.arbietdiary.calendar.entity.Calendar;
import com.arbietDiary.arbietdiary.calendar.entity.Date;
import com.arbietDiary.arbietdiary.calendar.repository.DateRepository;
import com.arbietDiary.arbietdiary.calendar.service.DateService;
import com.arbietDiary.arbietdiary.secret.SecretData;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DateServiceImpl implements DateService{
	private final DateRepository dateRepository;
	
	@Override
	public boolean makeCalendarDate(Calendar calendar) {
		List<Date> dateList = new ArrayList<Date>();
		LocalDateTime now = LocalDateTime.now();
		for(int i=0; i < SecretData.INIT_FORWARD_DAYS; i++) {
			Date date = makeDate(calendar, now.plusDays(i));
			dateList.add(date);
		}
		
		dateRepository.saveAll(dateList);
		return true;
	}
	
	@Override
	public Date makeDate(Calendar calendar, LocalDateTime day) {
		String[] dateTime = formatDtToStringSplitPoint(day).split("[.]");
		return Date.builder()
				.calendar(calendar)
				.year(toInteger(dateTime[0]))
				.month(toInteger(dateTime[1]))
				.day(toInteger(dateTime[2]))
				.dayOfWeek(day.getDayOfWeek().toString())
				.build();
	}
	@Override
	public String formatDtToStringSplitPoint(LocalDateTime regDt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
		return regDt != null ? regDt.format(formatter) : "";
	}
	
	public Integer toInteger(String data) {
		return Integer.parseInt(data);
	}
	
	@Override
	public boolean makeDays(List<Calendar> calendars) {
		List<Date> dates = new ArrayList<Date>();
		for(Calendar calendar : calendars) {
			dates.add(makeDate(calendar, LocalDateTime.now()));
		}
		dateRepository.saveAll(dates);
		return true;
	}
}
