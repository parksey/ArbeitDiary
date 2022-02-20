package com.arbietDiary.arbietdiary.member.service;

import java.util.List;

import com.arbietDiary.arbietdiary.calendar.model.FixedTimeInterface;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.UserListsDto;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.WorkUserListRequest;
import com.arbietDiary.arbietdiary.member.entity.Member;

public interface WorkService {

	List<FixedTimeInterface> getWorkTimeWProjectId(String projectIdText, String userId);

	boolean updateUserList(WorkUserListRequest userList, String userId);

	boolean add(UserListsDto user, Long projectId);

	String getWorkTime();

	boolean initWorkdays(Member member, Long projectId);

}
