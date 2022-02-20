package com.arbietDiary.arbietdiary.calendar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arbietDiary.arbietdiary.calendar.entity.Date;

@Repository
public interface DateRepository extends JpaRepository<Date, Long>{

	Optional<Date> findByCalendar_CalendarIdAndYearAndMonthAndDay(Long calendarId, int i, int j, int k);

	@Query("SELECT d FROM Date d WHERE d.calendar.calendarId = :calendarId AND d.dateId >= :todayId")
	List<Date> findAllByCalendarIdAndDateId(@Param("calendarId") Long calendarId, @Param("todayId") Long todayId);

	List<Date> findAllByCalendar_CalendarId(Long calendarId);

}
