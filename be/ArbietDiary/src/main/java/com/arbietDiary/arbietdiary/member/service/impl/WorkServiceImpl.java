package com.arbietDiary.arbietdiary.member.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arbietDiary.arbietdiary.calendar.model.FixedTimeInterface;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.FixedTimesDto;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.UserListsDto;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.WorkUserListRequest;
import com.arbietDiary.arbietdiary.calendar.model.type.DayEnum;
import com.arbietDiary.arbietdiary.member.entity.Member;
import com.arbietDiary.arbietdiary.member.entity.Work;
import com.arbietDiary.arbietdiary.member.repository.MemberRepository;
import com.arbietDiary.arbietdiary.member.repository.WorkRepository;
import com.arbietDiary.arbietdiary.member.service.WorkService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WorkServiceImpl implements WorkService{
	private final WorkRepository workRepository;
	private final MemberRepository memberRepository;
	
	@Override
	public List<FixedTimeInterface> getWorkTimeWProjectId(String projectIdText, String userId) {
		Long projectId = Long.parseLong(projectIdText);
		return workRepository.findByProjectIdAndMember_UserId(projectId, userId);
	}
	
	@Override
	public boolean add(UserListsDto user, Long projectId) {
		// TODO Auto-generated method stub
		if(user.getFixedtimes().isEmpty()) {
			System.out.println("[API AUTO] : add 없다.");
			return false;
		}
		
		for(FixedTimesDto time : user.getFixedtimes()) {
			Optional<Work> optionalWork = workRepository.findByMember_UserIdAndProjectIdAndWorkDay(user.getUserId(), projectId, time.getDayId());
			System.out.println(user.getUserId());
			if(!optionalWork.isPresent()) {
				System.out.println("새 WORK ID 추가");
				Optional<Member> optionalMember = memberRepository.findById(user.getUserId());
				
				if(!optionalMember.isPresent()) {
					System.out.println("[현재 멤버가 존재하지 않습니다.]");
					return false;
				}
				Work work = Work.builder()
						.workDay(time.getDayId())
						.workTime(time.getWorktime())
						.projectId(projectId)
						.member(optionalMember.get())
						.build();
				workRepository.save(work);
				continue;
			}
			System.out.println("[API AUTO] : 기존 업데이트");
			Work work = optionalWork.get();
			work.setWorkTime(time.getWorktime());
			workRepository.save(work);
		}
		System.out.println("[API AUTO] : 성공");
		return true;
	}
	
	
	@Override
	public boolean updateUserList(WorkUserListRequest userList, String userId) {
		if(userList.getUserList().isEmpty()) {
			System.out.println("존재하지 않습니다.");
			return false;
		}
		Long projectId = userList.getProjectId();
		for(UserListsDto user : userList.getUserList()) {
			boolean result = add(user,projectId);
			System.out.println(result);
		}
		return true;
	}

	@Override
	public String getWorkTime() {
		return "000000000000000000000000000000000000000000000000";
	}
	
	@Override
	public boolean initWorkdays(Member member, Long projectId) {
		List<Work> workList = new ArrayList<>();
		DayEnum[] days = DayEnum.values();
		for(DayEnum day : days) {
			workList.add(Work.builder()
					.workDay(day.name())
					.workTime(getWorkTime())
					.member(member)
					.projectId(projectId)
					.build());  
		}
		workRepository.saveAll(workList);
		return true;
	}
}
