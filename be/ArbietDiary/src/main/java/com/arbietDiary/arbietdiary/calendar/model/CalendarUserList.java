package com.arbietDiary.arbietdiary.calendar.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public interface CalendarUserList {
	@Value("#{target.id}")
	String getProjectId();
	String getProjectName();
	
	@Value("#{target.calendar.calendarId}")
	String getCalendarId();
	
	@Value("#{target.members}")
	List<userList> getUserList();
	interface userList{
		@Value("#{target.member.userName}")
		String getUserName();
		@Value("#{target.member.userId}")
		String getUserId();
		
		@Value("#{target.member.works}")
		List<times> getFixedtimes();
		interface times {
			@Value("#{target.workDay}")
			String getDayId();
			
			@Value("#{target.workTime}")
			String getWorktime();
		}
	}
}
