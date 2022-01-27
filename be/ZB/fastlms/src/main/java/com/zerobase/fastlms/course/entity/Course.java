package com.zerobase.fastlms.course.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	private String imagePath;
	private String keyword;
	private String subject;
	
	@Column(length = 1000)
	private String summary;
	
	@Lob
	private String contents;
	private long price;
	private long salePrice;
	private LocalDate saleEndDt;
	
	private LocalDateTime regDt;
	private LocalDateTime udtDt;
	
	Long categoryId;
}
