package com.arbietDiary.arbietdiary.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arbietDiary.arbietdiary.calendar.entity.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long>{

	boolean existsByProject_Id(Long id);

}
