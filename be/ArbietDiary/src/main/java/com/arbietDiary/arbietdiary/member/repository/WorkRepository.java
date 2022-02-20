package com.arbietDiary.arbietdiary.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arbietDiary.arbietdiary.calendar.model.FixedTimeInterface;
import com.arbietDiary.arbietdiary.member.entity.Work;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>{

	List<FixedTimeInterface> findByProjectIdAndMember_UserId(Long projectId, String userId);

	Optional<Work> findByMember_UserIdAndProjectIdAndWorkDay(String userId, Long projectId, String dayId);

}
