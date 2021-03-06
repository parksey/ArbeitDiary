package com.arbietDiary.arbietdiary.memberproject.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.arbietDiary.arbietdiary.member.entity.Member;
import com.arbietDiary.arbietdiary.project.entity.Project;
import com.arbietDiary.arbietdiary.project.model.type.ProjectRoleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="member_project")
@Getter
@Entity
public class MemberProject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Enumerated(EnumType.STRING)
	ProjectRoleType projectRole;
	
	LocalDateTime regDt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	Project project;
}
