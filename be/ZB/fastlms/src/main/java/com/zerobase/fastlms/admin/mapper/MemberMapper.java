package com.zerobase.fastlms.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;

@Mapper
public interface MemberMapper {
	List<MemberDto> selectList(MemberParam memberParam);
	long selectListCount(MemberParam memberParam);
} 
