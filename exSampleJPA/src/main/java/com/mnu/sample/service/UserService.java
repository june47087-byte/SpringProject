package com.mnu.sample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.dto.UserRequestDTO;
import com.mnu.sample.repository.UserRepository;
import com.mnu.sample.util.UserSHA256;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;
	
	public boolean userIdCheck(String userid) {
		return userRepository.existsByUserid(userid);
	}
/*
	// int 사용시 if문 이용해서 1,0 나타낸다. 
	public int userIdCheck(String userid) {
		if(userRepository.existsByUserid(userid))
			return 1;
		else
			return 0;
	}
*/
	// 등록 처리
	public int userInsert(UserRequestDTO userRequestDTO) {
		// 비번 암호화
		userRequestDTO.setPasswd(UserSHA256.getSHA256(userRequestDTO.getPasswd()));
		try {
			userRepository.save(userRequestDTO.toEntity());
			return 1;
		}catch(Exception e) {
			return 0;
		}
	}
}
