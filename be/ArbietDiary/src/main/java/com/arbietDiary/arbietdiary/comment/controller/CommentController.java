package com.arbietDiary.arbietdiary.comment.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arbietDiary.arbietdiary.comment.model.CommentInput;
import com.arbietDiary.arbietdiary.comment.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/dev")
@Controller
public class CommentController {
	private final CommentService commentService;
	
	@GetMapping("/comment")
	public String comment() {
		return "comment/comment";
	}
	
	@PostMapping("/comment")
	public String submitComment(CommentInput commentInput, Principal principal) {
		System.out.println("[Comment] : commentInput = " + commentInput);
		boolean result = commentService.add(commentInput.getCalendarId(), principal.getName(),commentInput.getDateId(), commentInput.getText());
		return "comment/comment";
	}
}
