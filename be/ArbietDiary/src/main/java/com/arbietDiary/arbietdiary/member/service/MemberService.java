package com.arbietDiary.arbietdiary.member.service;

import com.arbietDiary.arbietdiary.member.model.MemberInput;

public interface MemberService {

	boolean register(MemberInput memberInput);

	boolean emailAuth(String uuid);

	boolean sendResetPassword(MemberInput memberInput);

	boolean checkResetPasswordKey(String uuid);

	boolean resetPassword(String id, String password);

	boolean updateMemberPassword(MemberInput memberInput);

}
