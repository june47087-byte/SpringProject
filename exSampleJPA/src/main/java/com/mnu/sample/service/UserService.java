package com.mnu.sample.service;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.dto.UserRequestDTO;
import com.mnu.sample.dto.UserResponseDTO;
import com.mnu.sample.entity.UserEntity;
import com.mnu.sample.exception.CustomException;
import com.mnu.sample.repository.UserRepository;
import com.mnu.sample.util.UserSHA256;

import lombok.RequiredArgsConstructor;
/*
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
*/
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;

	//id중복 검사 - 1
	public int userIdCheck1(String userid) {
		if(userRepository.existsByUserid(userid))
			return 1;
		else
			return 0;
	}
	
	//id중복 검사 - 2
	public boolean userIdCheck2(String userid) {
		return userRepository.existsByUserid(userid);
	}
	
	//인증번호 생성용 메소드(4 ~ 6자리)
	private String tempRandomNumber() {
		Random r = new Random();
		StringBuffer numStr = new StringBuffer();
		for(int i=0; i<4; i++) { //6자리 생성시 i<6
			numStr.append(r.nextInt(10));
		}
		return numStr.toString();
	}
	/*
	//2. SMS 인증번호 발송
	public String sendSMS(String phoneNumber) {
		String tempNum = tempRandomNumber();//인증번호 생성메소드 호출
		DefaultMessageService messageService = 
				NurigoApp.INSTANCE.initialize(apiKey, apiSecret, url);
		Message message = new Message();
		message.setFrom(fromNumber);
		message.setTo(phoneNumber);
		message.setText("인증번호 : " + tempNum);
		
		messageService.sendOne(new SingleMessageSendingRequest(message));
		
		return tempNum;
	}
	*/
	//등록처리
	public int userInsert(UserRequestDTO userRequestDTO) {
		//비번 암호화
		userRequestDTO.setPasswd(UserSHA256.getSHA256(userRequestDTO.getPasswd()));
		try {
			userRepository.save(userRequestDTO.toEntity());
			return 1;
		}catch(Exception e) {
			return 0;
		}
		
	}
	
	//로그인 처리
	public UserResponseDTO userLogin(UserRequestDTO userRequestDTO) {
		UserEntity userEntity = userRepository.findByUserid(userRequestDTO.getUserid())
				.orElseThrow(()->new IllegalArgumentException("ID 없음"));
		//id가 존재하지 않으면 예외 발생
		if(!UserSHA256.getSHA256(userRequestDTO.getPasswd()).equals(userEntity.getPasswd())) {
			throw new IllegalArgumentException("비번오류");
			//비밀번호가 같지 않으면 예외 발생
		}
		
		return new UserResponseDTO(userEntity);
	}
	//throw(예외 발생), throws(예외가 발생시 떠 넘기다)
	
}
