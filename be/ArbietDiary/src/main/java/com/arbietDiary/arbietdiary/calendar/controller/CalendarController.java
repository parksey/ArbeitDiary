package com.arbietDiary.arbietdiary.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arbietDiary.arbietdiary.calendar.service.CalendarService;
import com.arbietDiary.arbietdiary.project.entity.Project;
import com.arbietDiary.arbietdiary.project.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CalendarController {
	private final ProjectRepository projectRepository;
	
	private final CalendarService calendarService;
	
	// Only Test
	@GetMapping("/makeC")
	public String makeC() {
		return "calendar/makeC";
	}
	
	@PostMapping("/makeC")
	public String submitMakeC(Long projectId) {
		System.out.println("[강제로 캘린더 생성] : projectId" + projectId);
		Project project = projectRepository.findById(projectId).get();
		boolean result = calendarService.makeCalendar(project);
		return "calendar/makeC";
	}
}
