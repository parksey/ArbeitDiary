package com.zerobase.fastlms.course.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zerobase.fastlms.course.model.TakeCourseCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class TakeCourse implements TakeCourseCode{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	long courseId;
	String userId;
	
	long payPrice; //결재금액
	String status; //상태( 수강신청, 결제완료, 수강취소
	
	private LocalDateTime regDt;	//신청일
}
