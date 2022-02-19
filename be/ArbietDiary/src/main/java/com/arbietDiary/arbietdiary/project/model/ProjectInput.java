package com.arbietDiary.arbietdiary.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectInput {
	/*
	 * 새로운 프로젝트 만들 때
	 */
	String projectName;
	
	/*
	 * 프로젝트 참여할 때
	 */
	Long projectId;
	Long joinId;
	
	String targetId;
}
