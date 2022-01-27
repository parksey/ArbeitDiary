package com.zerobase.fastlms.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.model.MemberStatusInput;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminMemberController {
	private final MemberService memberService;
	
	@GetMapping("/admin/member/list.do")
	public String memberList(Model model, MemberParam memberParam) {
		System.out.println("회원 리스트");
		List<MemberDto> members = memberService.list(memberParam);
		long totalCount = 0;
		if(!members.isEmpty()) {
			
			totalCount = members.get(0).getTotalCount();
		}
		String queryString = memberParam.getQueryString();
		PageUtil pageUtil = new PageUtil(totalCount, memberParam.getPageSize(), memberParam.getPageIndex(), queryString);
		model.addAttribute("lists", members);
		model.addAttribute("pager", pageUtil.pager());
		model.addAttribute("totalCount", totalCount);
		//return members;
		return "admin/member/list";
	}
	
	@GetMapping("/admin/member/detail.do")
	public String details(Model model, MemberParam memberParam) {
		MemberDto memberDto = memberService.detail(memberParam.getUserId());
		model.addAttribute("member", memberDto);
		return "admin/member/detail";
	}
	
	@PostMapping("/admin/member/status.do")
	public String status(Model model, MemberStatusInput memberStatusInput) {
		System.out.println("Model: "+memberStatusInput);
		boolean result = memberService.updateStatus(memberStatusInput.getUserId(), memberStatusInput.getUserStatus());
		return "redirect:/admin/member/detail.do?userId="+memberStatusInput.getUserId();
	}
	
	@PostMapping("/admin/member/password.do")
	public String passowrd(Model model, HttpServletRequest request,MemberStatusInput memberStatusInput) {
		System.out.println("Model: "+memberStatusInput);
		boolean result = false;
		try {
			result = memberService.adminSendPasswordEmail(memberStatusInput.getUserId());
		} catch(Exception e) {
			System.out.println(e);
		}
		return "redirect:/admin/member/detail.do?userId="+memberStatusInput.getUserId();
	}
}
