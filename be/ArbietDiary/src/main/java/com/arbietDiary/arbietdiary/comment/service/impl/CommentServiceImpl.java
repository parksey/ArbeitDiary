package com.arbietDiary.arbietdiary.comment.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arbietDiary.arbietdiary.calendar.entity.Date;
import com.arbietDiary.arbietdiary.calendar.repository.DateRepository;
import com.arbietDiary.arbietdiary.comment.entity.Comment;
import com.arbietDiary.arbietdiary.comment.repository.CommentRepository;
import com.arbietDiary.arbietdiary.comment.service.CommentService;
import com.arbietDiary.arbietdiary.member.entity.Member;
import com.arbietDiary.arbietdiary.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
	private final MemberRepository memberRepository;
	private final DateRepository dateRepository;
	private final CommentRepository commentRepository;
	
	@Override
	public boolean add(Long calendarId, String userId, Long dateId, String text) {
		// TODO Auto-generated method stub
		Optional<Member> member = memberRepository.findById(userId);
		if(!member.isPresent()) {
			System.out.println("회원 존재 X");
			return false;
		}
		Optional<Date> date = dateRepository.findById(dateId);
		if(!date.isPresent()) {
			System.out.println("날짜 존재 X");
			return false;
		}
		System.out.println("[Comment Add]");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		Comment comment = Comment.builder()
				.time(LocalDateTime.now().format(formatter).toString())
				.userId(userId)
				.text(text)
				.calendarId(calendarId)
				.userName(member.get().getUserName())
				.date(date.get())
				.build();
		
		commentRepository.save(comment);
		return true;
	}
}
