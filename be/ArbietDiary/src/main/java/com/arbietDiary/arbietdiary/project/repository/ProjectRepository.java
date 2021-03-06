package com.arbietDiary.arbietdiary.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arbietDiary.arbietdiary.calendar.model.CalendarUserList;
import com.arbietDiary.arbietdiary.project.entity.Project;
import com.arbietDiary.arbietdiary.project.model.ProjectListInterface;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

//	ProjectListInterface findById(Long id, Class<ProjectListInterface> class1);
//	CalendarUserList findById(Long projectId, Class<CalendarUserList> class1);
	<T>T findById(Long projectId, Class<T> type);
}
