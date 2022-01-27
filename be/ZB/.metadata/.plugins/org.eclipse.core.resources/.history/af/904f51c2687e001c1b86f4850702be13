package com.zerobase.fastlms.course.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.zerobase.fastlms.admin.controller.BaseController;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController{
	private final CourseService courseService;
	private final CategoryService categoryService;
	
	@GetMapping("/admin/course/list.do")
	public String list(Model model, CourseParam courseParam) {
		courseParam.init();
		System.out.println("1");
		List<CourseDto> courses = courseService.list(courseParam);
		
		long totalCount = 0;
		
		if(!CollectionUtils.isEmpty(courses)) {
			totalCount = courses.get(0).getTotalCount();
		}
		String queryString = courseParam.getQueryString();
		System.out.println("<<<courses"+courses);
		String pagerHtml = getPagerHtml(totalCount, courseParam.getPageSize(), courseParam.getPageIndex(), queryString);
		System.out.println("<<<courses"+courses);
		model.addAttribute("lists", courses);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);
		return "admin/course/list";
	}
	
	@GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
	public String add(Model model, HttpServletRequest request
			, CourseInput courseInput ) {
		boolean editMode = request.getRequestURI().contains("/edit.do");
		CourseDto detail = new CourseDto();
		if(editMode) {
			
			long id = courseInput.getId();
			CourseDto existCourse = courseService.getById(id);
			
			System.out.println(existCourse);
			if(existCourse == null) {
				//error
				model.addAttribute("error", "강좌정보 존재하지 않습니다.");
				return "common/error";
			}
			detail = existCourse;
		}
		model.addAttribute("category",categoryService.list());
		model.addAttribute("editMode",editMode);
		model.addAttribute("detail",detail);
		return "admin/course/add";
	}
	
	@PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
	public String addSubmit(Model model, CourseInput courseInput, HttpServletRequest request) {
		boolean editMode = request.getRequestURI().contains("/edit.do");
		boolean result = false;
		if(editMode) {
			long id = courseInput.getId();
			CourseDto existCourse = courseService.getById(id);
			
			System.out.println(existCourse);
			if(existCourse == null) {
				//error
				model.addAttribute("error", "강좌정보 존재하지 않습니다.");
				return "common/error";
			}
			result = courseService.set(courseInput);
		} else {
			result = courseService.add(courseInput);
		}
		
		return "redirect:/admin/course/list.do";
	}
	
	@PostMapping("/admin/course/delete.do")
	public String delete(Model model, CourseInput courseInput, HttpServletRequest request) {
		boolean result = courseService.delete(courseInput.getIdList());
			
		return "redirect:/admin/course/list.do";
	}
}
