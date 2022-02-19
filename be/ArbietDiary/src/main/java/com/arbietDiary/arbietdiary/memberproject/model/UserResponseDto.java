package com.arbietDiary.arbietdiary.memberproject.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.arbietDiary.arbietdiary.member.model.UserListInterface;
import com.arbietDiary.arbietdiary.project.model.ProjectListInterface;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDto {

	String userId;
	String userName;
	String phone;
	List<Projects> projects;
	
	public static List<UserListInterface.projects> toProject(List<UserListInterface.projects> projects,List<ProjectListInterface> projectUserList){
		List<UserListInterface.projects> result = new ArrayList<>();
		for(UserListInterface.projects project : projects) {
			//result.
		}
		return null;
	}
	

	private void setProjects(UserListInterface ui, List<ProjectListInterface> projectUserList) {
		List<Projects> projects = new ArrayList<>();
		for(ProjectListInterface project : projectUserList) {
			projects.add(new Projects(project));
		}
		int index = 0;
		for(UserListInterface.projects uiProject : ui.getProjects()) {
			projects.get(index).setJoinId(Projects.toString(uiProject.getId()));
			projects.get(index).setProjectRole(uiProject.getProjectRole());
			projects.get(index).setProjectRegDt(Projects.setProjectRegIdText(uiProject.getRegDt()));
			index++;
		}

		this.projects = projects;
	}
	
	public static UserResponseDto of(UserListInterface ui, List<ProjectListInterface> projectUserList) {
		UserResponseDto userResponse = UserResponseDto.builder()
				.userId(ui.getUserId())
				.userName(ui.getUserName())
				.phone(ui.getPhone())
				.build();
		
		userResponse.setProjects(ui, projectUserList);
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++=");
//		System.out.println(userResponse.getPhone());
//		System.out.println(userResponse.getUserId());
//		System.out.println(userResponse.getUserName());
//		System.out.println(userResponse.getProjects());
//		System.out.println(userResponse.getProjects().get(0).getJoinId());
//		System.out.println(userResponse.getProjects().get(0).getProjectId());
//		System.out.println(userResponse.getProjects().get(0).getProjectName());
//		System.out.println(userResponse.getProjects().get(0).getProjectRegDt());
//		System.out.println(userResponse.getProjects().get(0).getProjectRole());
//		System.out.println(userResponse.getProjects().get(0).getUserList());
//		System.out.println(userResponse.getProjects().get(0).getUserList().get(0).getPhone());
//		System.out.println(userResponse.getProjects().get(0).getUserList().get(0).getUserId());
//		System.out.println(userResponse.getProjects().get(0).getUserList().get(0).getUserName());
//		System.out.println(userResponse.getProjects().get(0).getUserList().get(0).getClass());
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++=");
		return userResponse;
	}

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Projects{
	String joinId;
	String projectRole;
	String projectRegDt;
	
	String projectId;
	String projectName;
	List<UserList> userList;
	
	public Projects(ProjectListInterface projectUser) {
		this.projectId = toString(projectUser.getProjectId());
		this.projectName = projectUser.getProjectName();
		this.userList = setUserList(projectUser.getUserList());
	}
	
	public List<UserList> setUserList(List<ProjectListInterface.userList> userList) {
		List<UserList> returnUserList = new ArrayList<>();
		for(ProjectListInterface.userList user : userList) {
			returnUserList.add(new UserList(user));
		}
		return returnUserList;
	}
	
	public static String toString(Long id) {
		return Long.toString(id);
	}
	
	public static String setProjectRegIdText(LocalDateTime regDt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy.MM.dd");
		return regDt != null ? regDt.format(formatter) : "";
	}
}

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
class UserList{
	String userName;
	String userId;
	String phone;
	boolean done;
	
	public UserList(ProjectListInterface.userList user) {
		this.done = false;
		this.userId = user.getUserId();
		this.userName = user.getUserName();
		this.phone = user.getPhone();
	}
}