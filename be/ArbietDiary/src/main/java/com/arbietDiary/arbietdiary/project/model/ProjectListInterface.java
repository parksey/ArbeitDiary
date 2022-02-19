package com.arbietDiary.arbietdiary.project.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public interface ProjectListInterface {
	@Value("#{target.id}")
	Long getProjectId();
	String getProjectName();

	@Value("#{target.members}")
	List<userList> getUserList();
	interface userList{
		@Value("#{target.member.userName}")
		String getUserName();
		@Value("#{target.member.userId}")
		String getUserId();
		@Value("#{target.member.phone}")
		String getPhone();
	}
}
