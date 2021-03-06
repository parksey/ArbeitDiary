package com.arbietDiary.arbietdiary.project.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.arbietDiary.arbietdiary.memberproject.entity.MemberProject;
import com.arbietDiary.arbietdiary.project.model.type.ProjectRoleType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseProjectList {
	Long projectId;
	String projectName;
	String userName;
	
	ProjectRoleType projectRole;
	LocalDateTime regDt;
	
	public static List<ResponseProjectList> memberProjectToResponseList(List<MemberProject> projectList) {
		List<ResponseProjectList> list = new ArrayList<ResponseProjectList>();
		for(MemberProject memberProject : projectList) {
			list.add(toResponse(memberProject));
		}
		return list;
	}
	
	public static ResponseProjectList toResponse(MemberProject memberProject) {
		
		return ResponseProjectList.builder()
				.projectId(memberProject.getProject().getId())
				.projectName(memberProject.getProject().getProjectName())
				.userName(memberProject.getMember().getUserName())
				.projectRole(memberProject.getProjectRole())
				.regDt(memberProject.getRegDt())
				.build();
	}
}
