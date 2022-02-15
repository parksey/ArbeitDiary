package com.arbietDiary.arbietdiary.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arbietDiary.arbietdiary.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

	Optional<Member> findByEmailAuthKey(String uuid);

	Optional<Member> findByUserIdAndUserName(String userId, String userName);

	Optional<Member> findByResetPasswordKey(String uuid);

}