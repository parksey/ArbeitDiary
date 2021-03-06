package com.arbietDiary.arbietdiary.datemember.service;

import java.util.List;

import com.arbietDiary.arbietdiary.calendar.entity.Date;
import com.arbietDiary.arbietdiary.calendar.model.dto.UserLists;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.DatesDto;

public interface DateMemberService {

	List<Date> exceptAutoTime(Long projectId, Long calendarId);

	int[] getTodayDay();

	boolean updateDailyWork(Long calendarId, List<UserLists> userList, List<Date> beforeDates);

	boolean loadDailyWork(Long calendarId, List<DatesDto> userList);

}
