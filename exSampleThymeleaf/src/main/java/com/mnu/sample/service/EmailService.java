package com.mnu.sample.service;

import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender emailSender;
	
	// 인증번호 6자리 생성 매소드
	public String createCode() {
		Random random = new Random();
		StringBuilder key = new StringBuilder();
		for(int i = 0; i < 6; i++) {
			key.append(random.nextInt(10));
		}
		return key.toString();
	}
	
	// 메일 양식 작성 및 전송
	public String sendEmail(String toEamil) {
		String authCode = createCode();
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEamil);
		message.setSubject("회원가입 인증 번호");
		message.setText("인증번호는 " + authCode + "입니다.");
		emailSender.send(message);
		return authCode; // 사용자가 입력한 값과 비교하기 위해 리턴(Redis나 세션에 저장 권장)
	}
}
