package com.arbietDiary.arbietdiary.datemember.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arbietDiary.arbietdiary.datemember.entity.DateMember;

@Repository
public interface DateMemberRepository extends JpaRepository<DateMember, Long>{

	void deleteAllByCalendarId(Long calendarId);

}
