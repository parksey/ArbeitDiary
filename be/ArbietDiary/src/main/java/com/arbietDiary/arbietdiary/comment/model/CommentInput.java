package com.arbietDiary.arbietdiary.comment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentInput {
	Long calendarId;
	Long dateId;
	String text;
}
