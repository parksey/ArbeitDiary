package com.arbietDiary.arbietdiary.member.model;

import java.time.LocalDateTime;
import java.util.List;

import com.arbietDiary.arbietdiary.project.entity.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;

public interface UserListInterface {
	String getUserId();
	String getUserName();
	String getPhone();
	
	List<projects> getProjects();
	interface projects{
		Long getId();
		String getProjectRole();
		LocalDateTime getRegDt();
		
		@JsonIgnore
		Project getProject();
	}
}
