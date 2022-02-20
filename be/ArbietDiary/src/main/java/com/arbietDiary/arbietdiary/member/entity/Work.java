package com.arbietDiary.arbietdiary.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "work")
@Entity
public class Work {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "work_id")
	Long workId;
	
	String workDay;
	@Column(length = 48)
	String workTime;
	
	Long projectId;
	
	
	@ManyToOne
	@JoinColumn(name = "userId")
	Member member;
}
