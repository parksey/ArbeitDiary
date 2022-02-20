package com.arbietDiary.arbietdiary.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arbietDiary.arbietdiary.member.entity.Member;
import com.arbietDiary.arbietdiary.member.model.UserListInterface;

public interface MemberRepository extends JpaRepository<Member, String>{

	Optional<Member> findByEmailAuthKey(String uuid);

	Optional<Member> findByUserIdAndUserName(String userId, String userName);

	Optional<Member> findByResetPasswordKey(String uuid);

	UserListInterface findByUserId(String userId);
}
