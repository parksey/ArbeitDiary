package com.arbietDiary.arbietdiary.datemember.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.arbietDiary.arbietdiary.calendar.entity.Date;
import com.arbietDiary.arbietdiary.calendar.model.CalendarUserList;
import com.arbietDiary.arbietdiary.calendar.model.dto.CalendarDto;
import com.arbietDiary.arbietdiary.calendar.model.dto.FixedTimes;
import com.arbietDiary.arbietdiary.calendar.model.dto.UserLists;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.DateUserDto;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.DatesDto;
import com.arbietDiary.arbietdiary.calendar.model.requestDto.DayIssues;
import com.arbietDiary.arbietdiary.calendar.repository.DateRepository;
import com.arbietDiary.arbietdiary.calendar.service.CalendarService;
import com.arbietDiary.arbietdiary.comment.entity.Comment;
import com.arbietDiary.arbietdiary.comment.repository.CommentRepository;
import com.arbietDiary.arbietdiary.datemember.entity.DateMember;
import com.arbietDiary.arbietdiary.datemember.repository.DateMemberRepository;
import com.arbietDiary.arbietdiary.datemember.service.DateMemberService;
import com.arbietDiary.arbietdiary.project.service.ProjectService;
import com.arbietDiary.arbietdiary.secret.SecretData;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DateMemberServiceImpl implements DateMemberService{
	private final DateRepository dateRepository;
	private final DateMemberRepository dateMemberRepository;
	private final CommentRepository commentRepository;
	
	private final ProjectService projectService;
	private final CalendarService calendarService;
	
	@Override
	public int[] getTodayDay(){
		String now = formatDtToString(LocalDateTime.now());
		int[] day = new int[3]; 
		day[0] = Integer.parseInt(now.substring(0,2));
		day[1] = Integer.parseInt(now.substring(2,4));
		day[2] = Integer.parseInt(now.substring(4));
		return day;
	}
	
	public String formatDtToString(LocalDateTime regDt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
		return regDt != null ? regDt.format(formatter) : "";
	}
	
	public boolean isWorkTime(String workTime) {
		for(char c : workTime.toCharArray()) {
			if(c == '1') {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Date> exceptAutoTime(Long projectId, Long calendarId){		
		int[] day = getTodayDay();
		Optional<Date> today = dateRepository.findByCalendar_CalendarIdAndYearAndMonthAndDay(calendarId, day[0], day[1], day[2]);
		System.out.println("[API LOAD: Get Today]");
		if(today.isEmpty()) {
			System.out.println("{API LOAD: ??????] : ???????????? ??????");
			return null;
		}

		List<Date> dates = dateRepository.findAllByCalendarIdAndDateId(calendarId, today.get().getDateId());		
		CalendarUserList calendarUserList = projectService.getUserList(projectId);
		CalendarDto calendarDto = new CalendarDto(calendarUserList); 
		CalendarDto result = calendarService.addWorkTime(calendarDto);
		
		List<UserLists> userList = result.getUserList();

		List<Date> dateWork = new ArrayList<Date>();
		List<DateMember> deleteWork = new ArrayList<DateMember>();
		for(Date date : dates) {
			List<DateMember> dayWorkers = date.getDateMember();
			if(dayWorkers.isEmpty()) {
				// ???????????? ??????
				dateWork.add(date);
				continue;
			}
			
			/*
			 * ???????????? : ?????? + ?????? ?????????
			 */
			List<DateMember> newWorker = new ArrayList<DateMember>();
			for(DateMember worker : dayWorkers) {
				System.out.println("[DateMember ID] : "+worker.getId());
				boolean isCoverWorker = true;
				// ?????? ???????????? ??????
				for(UserLists user : userList) {
					System.out.println(worker.getUserId()+":"+user.getUserId());
					if(!worker.getUserId().equals(user.getUserId())) {
						//??????????????? ?????? ???????????? ?????? = ????????? ??????
						continue;
					}
					
					
					//?????????
					isCoverWorker = false;
					for(FixedTimes time : user.getFixedtimes()) {
						System.out.println("[before]" + time.getWorktime() + ":" + worker.getWorkTime());
						System.out.println(time.getDayId() + ":" + date.getDayOfWeek());
						if(!time.getDayId().equals(date.getDayOfWeek())){
							// ?????? ?????? ??????
							continue;
						}
						if(!isWorkTime(time.getWorktime())) {
							//??? ?????? ?????? ???
							continue;
						}
						System.out.println("[????????????]");
						// ??? ?????? ???
						String compWorkTime = time.getWorktime();
						StringBuilder resetWorkTime = new StringBuilder(worker.getWorkTime());
						boolean isAddTime = false;
						for(int i=0; i < SecretData.TIMELENGTH; i++) {
							if(compWorkTime.charAt(i) == '1') {
								resetWorkTime.setCharAt(i, '0');
							} else if(resetWorkTime.charAt(i) == '1') {
								isAddTime = true;
							}
						}
						System.out.println("[After]" + resetWorkTime);
						if(isAddTime) {
							worker.setWorkTime(resetWorkTime.toString());
							newWorker.add(worker);
						}
						deleteWork.add(worker);
						break;
					}
					break;
				}	
				if(isCoverWorker) {
					//?????????
					newWorker.add(worker);
					deleteWork.add(worker);
				}	
			}
			date.setDateMember(newWorker);
			dateWork.add(date);
		}
		
		dateMemberRepository.deleteAll(deleteWork);
		return dateWork;
	}
	
	@Override
	public boolean updateDailyWork(Long calendarId, List<UserLists> userList, List<Date> beforeDates) {
		int[] day = getTodayDay();
		System.out.println("[day ???????????? ??????] : "+day);
		Optional<Date> todayDate = dateRepository.findByCalendar_CalendarIdAndYearAndMonthAndDay(calendarId, day[0], day[1], day[2]);
		Long today = todayDate.get().getDateId();
		System.out.println("[?????? ?????? ???????????? ??????] : "+today);
		
		/*
		 * ???????????? ??????
		 */
		List<Date> uploadDate = new ArrayList<Date>();
		List<DateMember> uploadMember = new ArrayList<>();	
		for(Date date : beforeDates) {
			List<DateMember> dayWorkers = date.getDateMember();
			System.out.println("[UPDATE] : ??????ID = "+ date.getDateId());
			System.out.println("[UPDATE] : ?????? =  " + date.getDayOfWeek());
			
			// ?????? ????????? ?????? ????????? ??????
			List<DateMember> todayWorker = new ArrayList<>();
			for(UserLists user : userList) {
				System.out.println("[UPDATE: UserList] : userId = "+user.getUserId());
				
				for(FixedTimes time : user.getFixedtimes()) {
					System.out.println("[UPDATE: dayId] : dayId = "+time.getDayId());
					if(!time.getDayId().equals(date.getDayOfWeek())) {
						System.out.println("[UPDATE] : ?????? = ?????? ????????? ?????????.");
						continue;
					}
					if(!isWorkTime(time.getWorktime())) {
						System.out.println("[UPDATE: time] : time = " + time.getWorktime());
						System.out.println("[UPDATE] : ?????? = ????????? ?????? ??????");
						//??? ?????? ?????? ???
						continue;
					}
					
					boolean isAddWorker = false;
					for(DateMember worker : dayWorkers) {
						if(!worker.getUserId().equals(user.getUserId())) {
							continue;
						}
						
						isAddWorker = true;
						String compWorkTime = time.getWorktime();
						StringBuilder resetWorkTime = new StringBuilder(worker.getWorkTime());
						for(int i=0; i < SecretData.TIMELENGTH; i++) {
							if(compWorkTime.charAt(i) == '1') {
								resetWorkTime.setCharAt(i, '1');
							}
						}
						worker.setWorkTime(resetWorkTime.toString());
						todayWorker.add(worker);
						uploadMember.add(worker);
						break;
					}
					if(!isAddWorker) {
						DateMember dateMember = new DateMember().builder()
								.date(date)
								.workTime(time.getWorktime())
								.userId(user.getUserId())
								.userName(user.getName())
								.calendarId(calendarId)
								.build(); 
						uploadMember.add(dateMember);
					}
					break;
				}
			}
			for(DateMember worker : dayWorkers) {
				boolean isTodayWork = false;
				for(DateMember todayWork : todayWorker) {
					if(worker.getUserId().equals(todayWork.getUserId())) {
						isTodayWork = true;
						break;
					}
				}
				if(!isTodayWork) {
					DateMember dateMember = new DateMember().builder()
							.date(date)
							.workTime(worker.getWorkTime())
							.userId(worker.getUserId())
							.userName(worker.getUserName())
							.calendarId(calendarId)
							.build(); 
					uploadMember.add(dateMember);
				}
			}
		}
			
		System.out.println("[UPDATE] : FINISH");
		dateMemberRepository.saveAll(uploadMember);
		
		return true;
	}
	
	@Transactional
	@Override
	public boolean loadDailyWork(Long calendarId, List<DatesDto> userList) {
		List<Date> dates = dateRepository.findAllByCalendar_CalendarId(calendarId);
		if(dates.isEmpty()) {
			System.out.println("[LOAD] : ??????");
			return false;
		}
		/*
		 * ?????? ??????
		 */
		List<DateMember> uploadMember = new ArrayList<>();
		List<Comment> uploadComment = new ArrayList<>();
		
		for(Date date : dates) {
			for(DatesDto userInfo : userList) {
				if(!date.getDateId().equals(userInfo.getDateId())) {
					System.out.println(date.getDateId()+" : "+userInfo.getDateId());
					continue;
				}
				System.out.println("??????");
				for(DateUserDto user : userInfo.getUsers()) {
					uploadMember.add(DateMember.builder()
							.date(date)
							.workTime(user.getWorktime())
							.userId(user.getUserId())
							.userName(user.getName())
							.calendarId(calendarId)
							.build());
				}
				
				for(DayIssues comment : userInfo.getDayIssues()) {
					uploadComment.add(Comment.builder()
							.time(comment.getTime())
							.text(comment.getText())
							.userName(comment.getName())
							.calendarId(calendarId)
							.date(date)
							.userId(comment.getUserId())
							.build());
				}
			}
		}
		
		System.out.println("[LOAD] : uploadComment = " + uploadComment.isEmpty());
		System.out.println("[LOAD] : uploadMember = " + uploadMember.isEmpty());
		System.out.println("======================================================");
		deleteCommentAndDateMember(calendarId);
		saveAllCommentAndDateMember(uploadComment, uploadMember);
		
		return true;
	}
	
	@Transactional
	public boolean saveAllCommentAndDateMember(List<Comment> uploadComment, List<DateMember> uploadMember) {
		commentRepository.saveAll(uploadComment);
		dateMemberRepository.saveAll(uploadMember);	
		return true;
	}
	
	@Transactional
	public boolean deleteCommentAndDateMember(Long calendarId) {
		commentRepository.deleteAllByCalendarId(calendarId);
		dateMemberRepository.deleteAllByCalendarId(calendarId);
		return true;
	}
}
