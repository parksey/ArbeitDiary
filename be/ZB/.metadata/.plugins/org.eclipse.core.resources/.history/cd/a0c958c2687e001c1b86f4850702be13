package com.zerobase.fastlms.course.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CourseController {
	private final CourseService courseService;
	private final CategoryService categoryService;
	
	@GetMapping("/course")
	public String course(Model model, CourseParam courseParam
			/*,@RequestParam(name="categoryId") long categoryId*/) {
		List<CourseDto> list = courseService.frontList(courseParam);
		List<CategoryDto> categoryList = categoryService.frontList(CategoryDto.builder().build());
		int count = 0;
		if(categoryList != null) {
			for(CategoryDto x : categoryList) {
				count += x.getCourseCount();
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("countAll", count);
		model.addAttribute("category", categoryList);
		return "course/index";
	}
	
	@GetMapping("/course/{courseId}")
	public String courseDetail(Model model, CourseParam courseParam) {
		CourseDto courseDto = courseService.frontDetail(courseParam.getCourseId());
		model.addAttribute("courseDetail",courseDto);
		System.out.println(courseDto.getId());
		return "course/detail";
	}
}
