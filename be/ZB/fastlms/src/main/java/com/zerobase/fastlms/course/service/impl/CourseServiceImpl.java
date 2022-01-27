package com.zerobase.fastlms.course.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.entity.TakeCourse;
import com.zerobase.fastlms.course.mapper.CourseMapper;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.repository.CourseRepository;
import com.zerobase.fastlms.course.repository.TakeCourseRepository;
import com.zerobase.fastlms.course.service.CourseService;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{
	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;
	
	private final TakeCourseRepository takeCourseRepository;
	
	private LocalDate getLocalDate(String value) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			return LocalDate.parse(value, formatter);
		}catch (Exception e) {
			
		}
		return null;
	}
	@Override
	public boolean add(CourseInput courseInput) {
		// TODO Auto-generated method stub
		LocalDate localDate = getLocalDate(courseInput.getSaleEndDtText());
		Course course = Course.builder()
				.categoryId(courseInput.getCategoryId())
				.subject(courseInput.getSubject())
				.regDt(LocalDateTime.now())
				.keyword(courseInput.getKeyword())
				.summary(courseInput.getSummary())
				.contents(courseInput.getContents())
				.price(courseInput.getPrice())
				.salePrice(courseInput.getSalePrice())
				.saleEndDt(localDate)
				.build();
		courseRepository.save(course);
				
		return true;
	}
	
	@Override
	public boolean set(CourseInput courseInput) {
		// TODO Auto-generated method stub
		LocalDate localDate = getLocalDate(courseInput.getSaleEndDtText());
		Optional<Course> optionalCourse = courseRepository.findById(courseInput.getId());
		if(!optionalCourse.isPresent()) {
			return false;
		}
		Course course = optionalCourse.get();
		
		course.setSubject(courseInput.getSubject());
		course.setUdtDt(LocalDateTime.now());
		course.setCategoryId(courseInput.getCategoryId());
		course.setKeyword(courseInput.getKeyword());
		course.setSummary(courseInput.getSummary());
		course.setContents(courseInput.getContents());
		course.setPrice(courseInput.getPrice());
		course.setSalePrice(courseInput.getSalePrice());
		course.setSaleEndDt(localDate);
		
		courseRepository.save(course);
		return true;
	}
	@Override
	public List<CourseDto> list(CourseParam courseParam) {
		// TODO Auto-generated method stub
		long totalCount = courseMapper.selectListCount(courseParam);
		System.out.println("hi");
		List<CourseDto> list = courseMapper.selectList(courseParam);
		System.out.println("??");
		if(!CollectionUtils.isEmpty(list)) {
			int i=0;
			for(CourseDto x : list) {
				x.setTotalCount(totalCount);
				x.setCourseNum(totalCount - courseParam.getPageStart() - i);
				i++;
			}
		}
		System.out.println("what?");
		return list;
	}
	@Override
	public CourseDto getById(long id) {
		// TODO Auto-generated method stub
		System.out.println(courseRepository.findById(id));
		return courseRepository.findById(id).map(CourseDto::of).orElse(null);
	}
	@Override
	public boolean delete(String idList) {
		// TODO Auto-generated method stub
		if(idList != null && idList.length() > 0) {
			String[] ids = idList.split(",");
			for(String id: ids) {
				long _id = 0L;
				try {
					_id = Long.parseLong(id);
				} catch(Exception e) {
					
				}
				if(_id > 0) {
					courseRepository.deleteById(_id);
				}
			}
		}
		return true;
	}
	
	@Override
	public List<CourseDto> frontList(CourseParam courseParam) {
		// TODO Auto-generated method stub
		if(courseParam.getCategoryId() < 1) {
			List<Course> list = courseRepository.findAll();
			return CourseDto.of(list);
		}
		// return courseRepository.findByCategoryId(courseParam.getCategoryId()).get().map(CourseDto::of),orElse(null);
		Optional<List<Course>> optionalList = courseRepository.findByCategoryId(courseParam.getCategoryId());
		if(optionalList.isPresent()) {
			return CourseDto.of(optionalList.get());
		}
		return null;
		
	}
	@Override
	public CourseDto frontDetail(long id) {
		// TODO Auto-generated method stub
		Optional<Course> optionalCourse = courseRepository.findById(id);
		
		if(!optionalCourse.isPresent()) {
			return null;
		}
		
		return CourseDto.of(optionalCourse.get());
	}
	@Override
	public ServiceResult req(TakeCourseInput takeCourseInput) {
		// TODO Auto-generated method stub
		ServiceResult result = new ServiceResult();
		Optional<Course> optionalCourse = courseRepository.findById(takeCourseInput.getCourseId());
		if(!optionalCourse.isPresent()) {
			result.setResult(false);
			result.setMessage("강좌정보 존재 X");
			return result;
		}
		Course course = optionalCourse.get();
		
		String[] statusList = {TakeCourse.STATUS_COMPLETE, TakeCourse.STATUS_REQ};
		long count = takeCourseRepository.countByCourseIdAndUserIdAndStatusIn(takeCourseInput.getCourseId(), takeCourseInput.getUserId(),Arrays.asList(statusList));
		if(count > 0) {
			result.setResult(false);
			result.setMessage("이미 수강중이십니다.");
			return result;
		}
		
		TakeCourse takeCourse = TakeCourse.builder()
				.courseId(course.getId())
				.userId(takeCourseInput.getUserId())
				.payPrice(course.getSalePrice())
				.regDt(LocalDateTime.now())
				.status(TakeCourse.STATUS_REQ)
				.build();
		takeCourseRepository.save(takeCourse);
		
		result.setResult(true);
		result.setMessage("수강 성공");
		
		return result;
	}
	

}
