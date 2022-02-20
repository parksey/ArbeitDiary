package com.arbietDiary.arbietdiary.calendar.model.requestDto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WorkUserListRequest {
	Long projectId;
	Long calendarId;
	String projectName;
	List<UserListsDto> userList;
	List<DatesDto> dates;
}
