package com.arbietDiary.arbietdiary.comment.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.arbietDiary.arbietdiary.calendar.entity.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comment")
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long replyId;
	
	String time;
	
	@Lob
	String text;
	
	Long calendarId;
	
	String userId;
	String userName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="date_id")
	Date date;
}
