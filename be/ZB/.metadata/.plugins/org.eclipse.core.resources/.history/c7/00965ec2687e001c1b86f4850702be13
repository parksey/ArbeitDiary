package com.zerobase.fastlms.course.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.zerobase.fastlms.course.entity.Course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseDto {
	long totalCount;
	long courseNum;
	
	Long id;
	
	String imagePath;
	String keyword;
	String subject;
	
	String summary;
	
	String contents;
	long price;
	long salePrice;
	LocalDate saleEndDt;
	
	LocalDateTime regDt;
	LocalDateTime udtDt;
	
	long categoryId;
	
	public static CourseDto of(Course course) {
		return CourseDto.builder()
				.imagePath(course.getImagePath())
				.id(course.getId())
				.keyword(course.getKeyword())
				.subject(course.getSubject())
				.summary(course.getSummary())
				.contents(course.getContents())
				.price(course.getPrice())
				.salePrice(course.getSalePrice())
				.saleEndDt(course.getSaleEndDt())
				.regDt(course.getRegDt())
				.udtDt(course.getUdtDt())
				.categoryId(course.getCategoryId())
				.build();
	}
	
	public static List<CourseDto> of(List<Course> courseList) {
		if(courseList == null) {
			return null;
		}
		List<CourseDto> list = new ArrayList<>();
		for(Course course : courseList) {
			list.add(CourseDto.of(course));
		}
		return list;		
	}
}
