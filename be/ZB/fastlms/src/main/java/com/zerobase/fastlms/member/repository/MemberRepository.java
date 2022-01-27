package com.zerobase.fastlms.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.fastlms.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
	Optional<Member> findByEmailAuthKey(String emailAuthKey);
	Optional<Member> findByUserIdAndUserName(String userId, String userName);
	Optional<Member> findByResetPasswordKey(String uuid);
}
