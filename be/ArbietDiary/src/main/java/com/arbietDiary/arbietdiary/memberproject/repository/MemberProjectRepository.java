package com.arbietDiary.arbietdiary.memberproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arbietDiary.arbietdiary.memberproject.entity.MemberProject;

@Repository
public interface MemberProjectRepository extends JpaRepository<MemberProject, Long>{

	List<MemberProject> findAllByMember_UserId(String userId);

	Optional<MemberProject> findByProject_IdAndMember_UserId(Long projectId, String userId);

	@Query("SELECT COUNT(mp) FROM MemberProject mp WHERE mp.project.id = :projectId AND mp.projectRole = 'USER'")
	Long countByProject_Id(@Param("projectId") Long id);

}
