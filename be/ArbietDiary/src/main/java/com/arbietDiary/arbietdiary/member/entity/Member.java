package com.arbietDiary.arbietdiary.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.arbietDiary.arbietdiary.member.model.MemberCode;
import com.arbietDiary.arbietdiary.memberproject.entity.MemberProject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "member")
@Getter
@Setter
@Entity
public class Member implements MemberCode{
	@Id
	@Column(name = "user_id")
	String userId;
	
	@Column(nullable = false)
	String userName;
	
	String phone;
	
	@Column(nullable = false)
	String password;
	
	LocalDateTime regDt;
	
	boolean emailAuthYn;
	String emailAuthKey;
	LocalDateTime emailAuthDt;
	
	String resetPasswordKey;
	LocalDateTime resetPasswordDt;
	
	boolean adminYn;
	
	String userState;
	
	String roles;
	
	@Column(length = 1000)
	String refreshToken;
	

	@OneToMany(mappedBy = "member",
			fetch = FetchType.LAZY)
	List<MemberProject> projects = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	List<Work> works = new ArrayList<Work>();
	
	public List<String> getRoleList(){
		if(this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<String>();
	}
	
	public void setRole(Collection<? extends GrantedAuthority> authorities) {
		//[ROLE_USER, ROLE_ADMIN]
		StringBuilder role = new StringBuilder();
		for(GrantedAuthority auth : authorities) {
			role.append(","+auth);
		}
		role.deleteCharAt(0);
		System.out.println(role.toString());
		this.roles = role.toString();
	}
}
