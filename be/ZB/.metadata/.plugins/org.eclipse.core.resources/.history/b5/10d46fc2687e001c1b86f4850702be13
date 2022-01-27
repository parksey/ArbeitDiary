package com.zerobase.fastlms.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;

@Mapper
public interface CourseMapper {
	List<CourseDto> selectList(CourseParam courseParam);
	long selectListCount(CourseParam courseParam);
}
