package com.zerobase.fastlms.course.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerobase.fastlms.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

	Optional<List<Course>> findByCategoryId(long categoryId);
}
