package com.arbietDiary.arbietdiary.calendar.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.arbietDiary.arbietdiary.calendar.model.CalendarDateList;
import com.arbietDiary.arbietdiary.calendar.model.CalendarUserList;

import lombok.Getter;

@Getter
public class CalendarDto {
	String calendarId;
	String projectId;
	String projectName;
	List<UserLists> userList = new ArrayList<>();
	List<CalendarDateList.CustomDate> dates = new ArrayList<>();
	
	public CalendarDto() {}
	public CalendarDto(CalendarUserList calendarUserList) {
		this.projectId = calendarUserList.getProjectId();
		this.projectName = calendarUserList.getProjectName();
		setUserList(calendarUserList.getUserList());
	}
	public CalendarDto(CalendarUserList calendarUserList,CalendarDateList calendarDateList) {
		this.calendarId = calendarDateList.getCalendarId();
		this.projectId = calendarUserList.getProjectId();
		this.projectName = calendarUserList.getProjectName();
		
		setUserList(calendarUserList.getUserList());
		setDates(calendarDateList.getDates());
	}
	
	public void setUserList(List<CalendarUserList.userList> userList){
		for(CalendarUserList.userList user: userList) {
			this.userList.add(new UserLists(user));
		}
	}
	public void setDates(List<CalendarDateList.CustomDate> dates) {
		this.dates = dates;
	}
}
