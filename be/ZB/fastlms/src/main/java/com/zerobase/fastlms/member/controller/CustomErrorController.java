package com.zerobase.fastlms.member.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/errosr")
	public String handleError() {
		return "/index.html";
	}

	public String getErrorPath(){
		return "/errors";
	}

}