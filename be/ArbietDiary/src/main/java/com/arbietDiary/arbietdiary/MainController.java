package com.arbietDiary.arbietdiary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class MainController {
	@GetMapping("/")
	public String index() {
		System.out.println("메인");
		return "index";
	}
}