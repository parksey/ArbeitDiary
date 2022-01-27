package com.zerobase.fastlms.course.service;

import java.util.List;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;

public interface CourseService {
	boolean add(CourseInput courseInput);

	List<CourseDto> list(CourseParam courseParam);
	
	CourseDto getById(long id);

	boolean set(CourseInput courseInput);

	boolean delete(String idList);

	List<CourseDto> frontList(CourseParam courseParam);

	CourseDto frontDetail(long id);

	ServiceResult req(TakeCourseInput takeCourseInput);
}
