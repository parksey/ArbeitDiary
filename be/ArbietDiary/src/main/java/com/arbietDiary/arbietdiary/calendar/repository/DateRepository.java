package com.arbietDiary.arbietdiary.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arbietDiary.arbietdiary.calendar.entity.Date;

public interface DateRepository extends JpaRepository<Date, Long>{

}
