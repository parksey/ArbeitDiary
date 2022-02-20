package com.arbietDiary.arbietdiary.calendar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arbietDiary.arbietdiary.calendar.entity.Calendar;
import com.arbietDiary.arbietdiary.calendar.model.CalendarDateList;
import com.arbietDiary.arbietdiary.calendar.model.ExList;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>{

	boolean existsByProject_Id(Long id);

	//@Query("SELECT c FROM Calendar c WHERE c.project.id = :projectId")
	CalendarDateList findByProject_Id(Long projectId);
	//<T>T findByProject_Id(Long projectId, Class<T> type);
}
