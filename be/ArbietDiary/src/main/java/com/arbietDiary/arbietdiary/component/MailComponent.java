package com.arbietDiary.arbietdiary.component;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MailComponent {
	private final JavaMailSender javaMailSender;
	
	public boolean sendMail(String mail, String subject, String text ) {
		System.out.println("[메일 전송]");
		
		MimeMessagePreparator msg = new MimeMessagePreparator() {			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
				mimeMessageHelper.setTo(mail);
				mimeMessageHelper.setSubject(subject);
				mimeMessageHelper.setText(text, true);
			}
		};
		boolean result = false;
		try {
			javaMailSender.send(msg);
			result = true;
		}catch(Exception e) {
			System.out.println("[메일 오류] : "+ e);
		}
		
		return result;
	}
}
