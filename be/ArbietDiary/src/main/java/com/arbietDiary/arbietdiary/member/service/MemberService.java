package com.arbietDiary.arbietdiary.member.service;

import java.util.List;

import com.arbietDiary.arbietdiary.member.model.CustomUserDetails;
import com.arbietDiary.arbietdiary.member.model.MemberInput;
import com.arbietDiary.arbietdiary.member.model.MemberTokenDto;

public interface MemberService {

	boolean register(MemberInput memberInput);

	boolean emailAuth(String uuid);

	boolean sendResetPassword(String userId, String userName);

	boolean checkResetPasswordKey(String uuid);

	boolean resetPassword(String id, String password);

	boolean updateMemberPassword(MemberInput memberInput);

	CustomUserDetails loadUserByUsername(String userId);

	CustomUserDetails apiUserDetail(String userId);

	MemberTokenDto getloginToken(CustomUserDetails principal);

	List<String> getUserId(String userPhone, String userName);
}
