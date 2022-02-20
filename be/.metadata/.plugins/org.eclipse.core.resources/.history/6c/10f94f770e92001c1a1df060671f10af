package com.arbietDiary.arbietdiary.calendar.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.arbietDiary.arbietdiary.comment.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "date")
@Entity
public class Date {
	@Id
	@Column(name = "date_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long dateId;
	
	Integer year;
	Integer month;
	Integer day;
	String dayOfWeek;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "calendar_id")
	Calendar calendar;
	
	@OneToMany(mappedBy = "date")
	List<Comment> comments = new ArrayList<>();
}
