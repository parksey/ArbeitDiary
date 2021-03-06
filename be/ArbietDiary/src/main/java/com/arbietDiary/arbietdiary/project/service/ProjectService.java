package com.arbietDiary.arbietdiary.project.service;

import java.util.List;

import com.arbietDiary.arbietdiary.calendar.model.CalendarUserList;
import com.arbietDiary.arbietdiary.project.model.ProjectInput;
import com.arbietDiary.arbietdiary.project.model.ResponseProjectList;

public interface ProjectService {
	boolean add (String userId,String projectName);
	List<ResponseProjectList> getOldProject(String userId);
	boolean join(String userId, Long projectId);
	boolean out(String userId, ProjectInput projectInput);
	String responseOldProject(String userId);
	CalendarUserList getUserList(Long projectId);
}
