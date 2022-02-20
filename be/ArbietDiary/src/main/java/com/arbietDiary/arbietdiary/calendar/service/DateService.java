package com.arbietDiary.arbietdiary.calendar.service;

import java.time.LocalDateTime;
import java.util.List;

import com.arbietDiary.arbietdiary.calendar.entity.Calendar;
import com.arbietDiary.arbietdiary.calendar.entity.Date;

public interface DateService{

	boolean makeCalendarDate(Calendar calendar);

	Date makeDate(Calendar calendar, LocalDateTime day);

	String formatDtToStringSplitPoint(LocalDateTime regDt);

	boolean makeDays(List<Calendar> calendars);

}
