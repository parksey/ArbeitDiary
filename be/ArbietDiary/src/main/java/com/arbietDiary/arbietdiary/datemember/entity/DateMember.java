package com.arbietDiary.arbietdiary.datemember.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.arbietDiary.arbietdiary.calendar.entity.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "date_member")
@Entity
public class DateMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String workTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "date_id")
	Date date;
	
	String userName;
	String userId;
	Long calendarId;
}
