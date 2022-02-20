package com.arbietDiary.arbietdiary.calendar.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.arbietDiary.arbietdiary.project.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "calendar")
@Entity
public class Calendar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "calendar_id")
	Long calendarId;
	
	/*
	 * 프로젝트 확장시 사용
	 */
	
	@OneToOne
	@JoinColumn(name = "project_id")
	Project project;
	
	@OneToMany(mappedBy = "calendar")
	List<Date> dates = new ArrayList<Date>();
	
}
