package com.zerobase.fastlms.course.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TakeCourseDto {
	Long id;
	
	long courseId;
	String userId;
	
	long payPrice; //결재금액
	String status; //상태( 수강신청, 결제완료, 수강취소
	
	LocalDateTime regDt;	//신청일
	
	String userName;
	String phone;
	String subject;
	
	long totalCount;
	long courseNum;
}
