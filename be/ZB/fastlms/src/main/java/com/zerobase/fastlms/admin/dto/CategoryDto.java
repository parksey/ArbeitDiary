package com.zerobase.fastlms.admin.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.zerobase.fastlms.admin.entity.Category;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryDto {
	private long id;
	
	String categoryName;
	int sortValue;
	boolean usingYn;
	
	int courseCount;
	
	public static List<CategoryDto> of(List<Category> categories){
		if(categories != null) {
			List<CategoryDto> categoryDto = new ArrayList<>();
			for(Category category : categories) {
				categoryDto.add(of(category));
			}
			return categoryDto;
		}
		return null;
	}
	
	public static CategoryDto of(Category category) {
		return CategoryDto.builder()
				.id(category.getId())
				.categoryName(category.getCategoryName())
				.sortValue(category.getSortValue())
				.usingYn(category.isUsingYn())
				.build();
	}
			
	
}
