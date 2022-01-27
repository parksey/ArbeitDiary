package com.zerobase.fastlms.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.TakeCourseParam;

@Mapper
public interface TakeCourseMapper {
	List<TakeCourseDto> selectList(TakeCourseParam courseParam);
	long selectListCount(TakeCourseParam courseParam);
}
