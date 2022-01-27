package com.zerobase.fastlms.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zerobase.fastlms.admin.dto.CategoryDto;

@Mapper
public interface CategoryMapper {
	List<CategoryDto> select(CategoryDto categoryDto);
}
