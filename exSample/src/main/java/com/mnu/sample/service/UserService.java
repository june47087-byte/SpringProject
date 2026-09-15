package com.mnu.sample.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.UserDTO;
import com.mnu.sample.domain.UserMyPageDTO;
import com.mnu.sample.mapper.UserMapper;
import com.mnu.sample.util.UserSHA256;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class UserService {
	//coolsms 갑 저장
	@Value("${coolsms.apikey}")
	private String apiKey;
	@Value("${coolsms.apisecret}")
	private String apiSecret;
	@Value("${coolsms.fromnumber}")
	private String fromNumber;
	@Value("${coolsms.url}")
	private String url;
	//UserMapper 주입
	@Autowired
	private UserMapper userMapper;
	UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	//1. id 중복 검사
	public int userIdCheck(String userid) {
		return userMapper.userIdCheck(userid);
	}
	//인증번호 생성용 메소드
	private String tempRandomNumber() {
		Random r = new Random();
		StringBuffer numStr = new StringBuffer();
		for(int i = 0; i < 4; i++) {
			numStr.append(r.nextInt(10));
		}
		return numStr.toString();
	}
	// SMS 인증번호 발송
	public String sendSMS(String phoneNumber) {
		String tempNum = tempRandomNumber(); // 인증번호 생성 메소드 호출
		DefaultMessageService messageService =
				NurigoApp.INSTANCE.initialize(apiKey, apiSecret, url);
		Message message = new Message();
		message.setFrom(fromNumber);
		message.setTo(phoneNumber);
		message.setText("인증번호 : " + tempNum);
		
		messageService.sendOne(new SingleMessageSendingRequest(message));
		return tempNum;
	}
	// 유저 정보 등록
	public int userInsert(UserDTO userDTO) {
		// 비밀번호는 암호화해서 db에 넣는다.
		userDTO.setPasswd(UserSHA256.getSHA256(userDTO.getPasswd()));
		return userMapper.userInsert(userDTO);
	}
	
	// 로그인
	public UserDTO userLogin(UserDTO userDTO) {
		// db에는 암호화 되어 있기에 마찬가지로 암호화 해서 db랑 대조해야한다.
		userDTO.setPasswd(UserSHA256.getSHA256(userDTO.getPasswd()));
		return userMapper.userLogin(userDTO);
	}
	
	// 마지막 로그이 시간 업데이트
	public int userLastTimeUpdate(String userid) {
		return userMapper.userLastTimeUpdate(userid);
	}

	// 회원정보 수정
	public int userModify(UserDTO userDTO) {
		userDTO.setPasswd(UserSHA256.getSHA256(userDTO.getPasswd()));
		return userMapper.userModify(userDTO);
	}

	// 회원정보 단건 조회
	public UserDTO userFind(String userid) {
		return userMapper.userFind(userid);
	}

	// 마이 페이지 조회
	public UserMyPageDTO userMyPage(String name) {
		return userMapper.userMyPage(name);
	}
}
