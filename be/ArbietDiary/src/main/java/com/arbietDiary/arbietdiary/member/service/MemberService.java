package com.arbietDiary.arbietdiary.member.service;

import com.arbietDiary.arbietdiary.member.model.CustomUserDetails;
import com.arbietDiary.arbietdiary.member.model.MemberInput;
import com.arbietDiary.arbietdiary.member.model.MemberTokenDto;

public interface MemberService {

	boolean register(MemberInput memberInput);

	boolean emailAuth(String uuid);

	boolean sendResetPassword(MemberInput memberInput);

	boolean checkResetPasswordKey(String uuid);

	boolean resetPassword(String id, String password);

	boolean updateMemberPassword(MemberInput memberInput);

	CustomUserDetails loadUserByUsername(String userId);

	CustomUserDetails apiUserDetail(String userId);

	MemberTokenDto getloginToken(CustomUserDetails principal);

}
