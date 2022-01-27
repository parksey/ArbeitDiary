package com.zerobase.fastlms.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.mapper.CategoryMapper;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private Sort getSortBySortValueDesc() {
		return Sort.by(Sort.Direction.DESC, "sortValue");
	}
	
	@Override
	public List<CategoryDto> list(){
		List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc());
		return CategoryDto.of(categories);
		// return categoryRepository.findAllOrderBySortValueDesc().map(CategoryDto::of).orElse(null);
	}
	
	@Override
	public boolean add(String categoryName) {
		Category category = Category.builder()
				.categoryName(categoryName)
				.usingYn(true)
				.sortValue(0)
				.build();
		categoryRepository.save(category);
	
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(CategoryInput categoryInput) {
		// TODO Auto-generated method stub
		Optional<Category> optioanlCategory = categoryRepository.findById(categoryInput.getId());
		if(!optioanlCategory.isPresent()) {
			return false;
		}
		Category category = optioanlCategory.get();
		category.setCategoryName(categoryInput.getCategoryName());
		category.setSortValue(categoryInput.getSortValue());
		category.setUsingYn(categoryInput.isUsingYn());
		System.out.println("변경 완료 : "+category);
		categoryRepository.save(category);
         System.out.println("완료 후 전체 :" +categoryRepository.findAll());
		return true;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if(!optionalCategory.isPresent()) {
			System.out.println("?");
			return false;
		}
		System.out.println("??");
		categoryRepository.deleteById(id);
		return true;
	}

	@Override
	public List<CategoryDto> frontList(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		return categoryMapper.select(categoryDto);
	}

}
