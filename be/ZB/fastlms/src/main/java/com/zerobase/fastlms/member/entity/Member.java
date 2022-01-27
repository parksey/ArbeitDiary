package com.zerobase.fastlms.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.zerobase.fastlms.admin.model.MemberCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice.Local;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements MemberCode{
	@Id
	private String userId;
	 
	private String userName;
	private String phone;
	private String password;
	private LocalDateTime regDt;
	
	private boolean emailAuthYn;
	private String emailAuthKey;
	private LocalDateTime emailAuthDt;
	
	private String resetPasswordKey;
	private LocalDateTime resetPasswordDt;
	
	private boolean adminYn;
	
	private String userState;
}
