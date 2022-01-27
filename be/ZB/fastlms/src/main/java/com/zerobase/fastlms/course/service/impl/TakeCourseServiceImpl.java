package com.zerobase.fastlms.course.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.mapper.TakeCourseMapper;
import com.zerobase.fastlms.course.model.TakeCourseParam;
import com.zerobase.fastlms.course.service.TakeCourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TakeCourseServiceImpl implements TakeCourseService {
	private final TakeCourseMapper takeCourseMapper;
	
	
	@Override
	public List<TakeCourseDto> list(TakeCourseParam takeCourseParam) {
		// TODO Auto-generated method stub
		long totalCount = takeCourseMapper.selectListCount(takeCourseParam);
		System.out.println("hi");
		List<TakeCourseDto> list = takeCourseMapper.selectList(takeCourseParam);
		System.out.println("??");
		if(!CollectionUtils.isEmpty(list)) {
			int i=0;
			for(TakeCourseDto x : list) {
				x.setTotalCount(totalCount);
				x.setCourseNum(totalCount - takeCourseParam.getPageStart() - i);
				i++;
			}
		}
		return list;
	}

}
