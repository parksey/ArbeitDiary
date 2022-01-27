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
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.TakeCourseParam;
import com.zerobase.fastlms.course.service.TakeCourseService;
import com.zerobase.fastlms.course.service.impl.TakeCourseServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminTakeCourseController extends BaseController{
	private final TakeCourseService takeCourseService;
	private final CategoryService categoryService;
	
	@GetMapping("/admin/takecourse/list.do")
	public String list(Model model, TakeCourseParam takecourseParam) {
		takecourseParam.init();
		List<TakeCourseDto> courses = takeCourseService.list(takecourseParam);
		
		long totalCount = 0;
		
		if(!CollectionUtils.isEmpty(courses)) {
			totalCount = courses.get(0).getTotalCount();
		}
		String queryString = takecourseParam.getQueryString();
		System.out.println("<<<courses"+courses);
		String pagerHtml = getPagerHtml(totalCount, takecourseParam.getPageSize(), takecourseParam.getPageIndex(), queryString);
		System.out.println("<<<courses"+courses);
		model.addAttribute("lists", courses);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);
		return "admin/takecourse/list";
	}
	
	
}
