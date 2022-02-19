package com.arbietDiary.arbietdiary.member.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.arbietDiary.arbietdiary.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
	private Member member;
	private Collection<? extends GrantedAuthority> authorities;

	public <T> CustomUserDetails(Member member, List<T> roleList) {
		this.member= member;
		this.authorities = this.stringToGrantedAuth(roleList);
	}

	private <T> Collection<? extends GrantedAuthority> stringToGrantedAuth(List<T> roleList){
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		for(T role : roleList) {
			authList.add(new SimpleGrantedAuthority(role.toString()));
		}
		return authList;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getUserId();
	}
	

	public void setMemberRefreshToken(String token) {
		this.member.setRefreshToken(token);
	}
	
	public String getMemberRefreshToken() {
		return this.member.getRefreshToken();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
