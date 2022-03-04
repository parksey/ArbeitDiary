package com.arbietDiary.arbietdiary.calendar.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arbietDiary.arbietdiary.calendar.entity.Calendar;
import com.arbietDiary.arbietdiary.calendar.entity.Date;
import com.arbietDiary.arbietdiary.calendar.model.CalendarDateList;
import com.arbietDiary.arbietdiary.calendar.model.CalendarInput;
import com.arbietDiary.arbietdiary.calendar.model.CalendarUserList;
import com.arbietDiary.arbietdiary.calendar.model.ExList;
import com.arbietDiary.arbietdiary.calendar.model.dto.CalendarDto;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.WorkUserListRequest;
import com.arbietDiary.arbietdiary.calendar.repository.CalendarRepository;
import com.arbietDiary.arbietdiary.calendar.service.CalendarService;
import com.arbietDiary.arbietdiary.datemember.service.DateMemberService;
import com.arbietDiary.arbietdiary.member.service.WorkService;
import com.arbietDiary.arbietdiary.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiCalendarController {
	private final ProjectService projectService;
	private final CalendarService calendarService;
	private final WorkService workService;
	private final DateMemberService dateMemberService;

	@PostMapping("/api/load")
	public ResponseEntity<?> load(@RequestBody CalendarInput calendarInput, Principal principal){
		System.out.println("[API LOAD]");
		CalendarUserList calendarUserList = projectService.getUserList(calendarInput.getProjectId());
		System.out.println("[API LOAD] : calendarUserList 성공");
		
		CalendarDateList calendarDateList = calendarService.getCalendarDateList(calendarInput.getProjectId());
		System.out.println("[API LOAD] : calendarDateList 성공");
		
		CalendarDto resultCalendar = calendarService.getCalendarDto(calendarUserList, calendarDateList);
		System.out.println("[API LOAD: GET DTO] : 성공");

		return ResponseEntity.ok().body(resultCalendar);
	}
	
	@PostMapping("/api/auto")
	public ResponseEntity<?> self(@RequestBody WorkUserListRequest workUserList, Principal principal){
		System.out.println("[API AUTO] : workUserList = " + workUserList);
		List<Date> beforeUserList = dateMemberService.exceptAutoTime(workUserList.getProjectId(), workUserList.getCalendarId());
		System.out.println("[API AUTO: 이전값 찾기] : 완료");
		
		boolean isUserListUpdate = workService.updateUserList(workUserList, principal.getName());
		System.out.println("[API AUTO: 현재 유저 업데이트] : 완료");
		
		CalendarUserList calendarUserList = projectService.getUserList(workUserList.getProjectId());
		
		CalendarDto resultCalendar = calendarService.getCalendarDto(calendarUserList);
		
		System.out.println("[API AUTO: userList값 가져오기] : 성공");
		
		boolean result = dateMemberService.updateDailyWork(workUserList.getCalendarId(), resultCalendar.getUserList(), beforeUserList);
		return ResponseEntity.ok().body(resultCalendar);
	}
	
	@PostMapping("/api/update")
	public ResponseEntity<?> calendar(@RequestBody WorkUserListRequest workUserList, Principal principal){
		System.out.println("[API UPDATE] : 요청");
		boolean result = dateMemberService.loadDailyWork(workUserList.getCalendarId(), workUserList.getDates());
		System.out.println("[API UPDATE] : loadDailyWork = " + result);
		
		CalendarUserList calendarUserList = projectService.getUserList(workUserList.getProjectId());
		CalendarDateList calendarDateList = calendarService.getCalendarDateList(workUserList.getProjectId());
		
		CalendarDto resultCalendar = calendarService.getCalendarDto(calendarUserList, calendarDateList);
		return ResponseEntity.ok().body(resultCalendar);
	}
}
