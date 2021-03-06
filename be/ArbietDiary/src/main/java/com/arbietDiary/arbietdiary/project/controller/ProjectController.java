package com.arbietDiary.arbietdiary.project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arbietDiary.arbietdiary.project.model.ProjectInput;
import com.arbietDiary.arbietdiary.project.model.ResponseProjectList;
import com.arbietDiary.arbietdiary.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/dev")
@Controller
public class ProjectController {
	private final ProjectService projectService;

	@GetMapping("/newproject")
	public String newProject() {
		return "projects/newproject";
	}
	
	@PostMapping("/newproject")
	public String submitNewProject(ProjectInput projectInput, Principal principal) {
		System.out.println("[새프로젝트] : 요청");
		boolean result = projectService.add(principal.getName(), projectInput.getProjectName());
		return "projects/newproject";
	}
	
	@GetMapping("/oldproject")
	public String oldProject(Model model, Principal principal) {
		List<ResponseProjectList> projectList = projectService.getOldProject(principal.getName());
		System.out.println(projectList.get(0).getProjectId());
		
		model.addAttribute("lists", projectList);
		return "projects/oldproject";
	}
	
	@GetMapping("/joinProject")
	public String joinProject() {
		return "projects/join";
	}
	
	@PostMapping("/joinProject")
	public String submitJoinProject(ProjectInput projectInput, Principal principal) {
		System.out.println("[Join] : 요청");
		System.out.println(projectInput);
		
		boolean result = projectService.join(principal.getName(), projectInput.getProjectId());
		return "projects/join";
	}
	
	@GetMapping("/outProject")
	public String outProject() {
		return "projects/out";
	}
	
	@PostMapping("/outProject")
	public String submitOutProject(ProjectInput projectInput, Principal principal) {
		System.out.println("[Out] : 요청");
		boolean result = projectService.out(principal.getName(), projectInput);
		return "projects/out";
	}
}
