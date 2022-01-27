package com.zerobase.fastlms.admin.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import com.zerobase.fastlms.admin.service.CategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class AdminCategoryController {
	private final CategoryService categoryService;
	
	@GetMapping("/admin/category/list.do")
	public String list(Model model, MemberParam memberParam, HttpServletRequest request){
		System.out.println("<<list page>>");
		List<CategoryDto> categoryDto = categoryService.list();
		model.addAttribute("lists", categoryDto);
		System.out.println(categoryDto);
		return "admin/category/list";
	}
	
	@PostMapping("/admin/category/add.do")
	public String add(Model model, CategoryInput categoryInput) {
		boolean result = categoryService.add(categoryInput.getCategoryName());
		return "redirect:/admin/category/list.do";
	}
	
	@PostMapping("/admin/category/delete.do")
	public String delete(HttpServletRequest request, Model model, CategoryInput categoryInput) {
	
		boolean result = categoryService.delete(categoryInput.getId());
		return "redirect:/admin/category/list.do";
	}
	
	@PostMapping("/admin/category/update.do")
	public String update(HttpServletRequest request, Model model, CategoryInput categoryInput) {
		System.out.println("<<<버튼 수행>>>");
		System.out.println("input : "+categoryInput);
		boolean result = categoryService.update(categoryInput);
		return "redirect:/admin/category/list.do";
	}
}

