package com.arbietDiary.arbietdiary.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arbietDiary.arbietdiary.comment.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	void deleteAllByCalendarId(Long calendarId);

}
