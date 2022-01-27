package com.zerobase.fastlms;

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
	
	@GetMapping("/error/denied")
	public String error() {
		return "/error/denied";
	}
}
