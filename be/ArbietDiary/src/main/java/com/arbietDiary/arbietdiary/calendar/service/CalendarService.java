package com.arbietDiary.arbietdiary.calendar.service;

import com.arbietDiary.arbietdiary.calendar.model.CalendarDateList;
import com.arbietDiary.arbietdiary.calendar.model.CalendarUserList;
import com.arbietDiary.arbietdiary.calendar.model.dto.CalendarDto;
import com.arbietDiary.arbietdiary.project.entity.Project;

public interface CalendarService {

	boolean makeCalendar(Project project);

	boolean makeDaysAllCalendar();

	CalendarDto addWorkTime(CalendarDto calendarDto);

	CalendarDateList getCalendarDateList(Long projectId);

	CalendarDto getCalendarDto(CalendarUserList calendarUserList, CalendarDateList calendarDateList);

	CalendarDto getCalendarDto(CalendarUserList calendarUserList);

}
