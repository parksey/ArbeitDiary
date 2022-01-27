package com.zerobase.fastlms.admin.service;

import java.util.List;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.CategoryInput;

public interface CategoryService {
	boolean add(String categoryName);
	boolean update(CategoryInput categoryInput);
	boolean delete(long id);
	List<CategoryDto> list();
	List<CategoryDto> frontList(CategoryDto categoryDto);
};
