package com.arbietDiary.arbietdiary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class MainController {
	@GetMapping("/dev")
	public String devIndex() {
		System.out.println("메인");
		return "indexdev";
	}
	
}
