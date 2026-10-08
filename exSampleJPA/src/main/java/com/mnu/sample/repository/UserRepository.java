package com.mnu.sample.repository;

import java.beans.Transient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
	//count() // 카운트
	//findAll()	//전체목록
	//save(entity)// 등록, 수정
	//findById()//기본키를 이용한 검색
	//delete()//삭제
	
	// 1.id를 이용한 사용자 검색
	UserEntity findByUserid(String userid);
	
	// 2.id 중복검사
	boolean existsByUserid(String userid);
	
	// 3. 회원가입(save() 이용)
	
}
