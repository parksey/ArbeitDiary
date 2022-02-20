package com.arbietDiary.arbietdiary.calendar.model.requestDto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListsDto {
	String userId;
	String name;
	List<FixedTimesDto> fixedtimes = new ArrayList<>();
}
