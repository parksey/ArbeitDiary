package com.arbietDiary.arbietdiary.project.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arbietDiary.arbietdiary.project.model.ProjectInput;
import com.arbietDiary.arbietdiary.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiProjectController {
	private final ProjectService projectService;
	
	@GetMapping("/api/oldproject")
//	@RequestMapping(value = "/api/oldproject", method = RequestMethod.POST)
	public ResponseEntity<?> list(Principal principal){
		System.out.println("[API OldProject] : 요청");
		if(principal.getName().isBlank()) {
			return ResponseEntity.ok().body("비어 있습니다.");
		}
		System.out.println("[API OldProject] : name = " + principal.getName());
		
		String result = projectService.responseOldProject(principal.getName());
		System.out.println("[API OldProject] : result = "+result);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/newproject")
	public ResponseEntity<?> submitNewproject(Principal principal,@RequestBody ProjectInput projectInput){
		System.out.println("[API NewProject] : 요청");
		boolean result = projectService.add(principal.getName(), projectInput.getProjectName());
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/deleteproject")
	public ResponseEntity<?> submitDeleteproject(Principal principal,@RequestBody ProjectInput projectInput){
		System.out.println("[API DeleteProject] : 요청");
		boolean result = projectService.out(principal.getName(), projectInput);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/joinproject")
	public ResponseEntity<?> submitJoinproject(Principal principal,@RequestBody ProjectInput projectInput){
		System.out.println("[post : newproejct]");
		System.out.println("[post : newproejct] : name = " +projectInput+ principal.getName());
		boolean result = projectService.join(principal.getName(), projectInput.getProjectId());
		return ResponseEntity.ok().body(result);
	}
}
