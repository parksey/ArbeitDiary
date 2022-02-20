package com.arbietDiary.arbietdiary.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arbietDiary.arbietdiary.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
