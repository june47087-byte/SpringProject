package com.mnu.sample.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.UserDTO;
import com.mnu.sample.domain.UserMyPageDTO;

@Mapper
public interface UserMapper {
	//1. id 중복 검사
	public int userIdCheck(String userid);
	//2. 유저정보 등록
	public int userInsert(UserDTO userDTO);
	// 3. 로그인
	public UserDTO userLogin(UserDTO userDTO);
	// 4. 마지막 로그인 날짜 업데이트
	public int userLastTimeUpdate(String userid);
	// 5. 회원정보 수정
	public int userModify(UserDTO userDTO);
	// 6. 회원정보 단건 조회
	public UserDTO userFind(String userid);
	// 7. 마이 페이지 조회
	public UserMyPageDTO userMyPage(String name);
}