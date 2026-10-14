package com.mnu.sample.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnu.sample.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
	//count() // 카운트
	//findAll()	//전체목록
	//save(entity)// 등록, 수정(update)
	//findById()//기본키를 이용한 검색
	//delete()//삭제

	//1. userid이용한 사용자 검색
	Optional<UserEntity> findByUserid(String userid);
	
	//2.id중복검사
	boolean existsByUserid(String userid);//userid 존재하면(true)
	
	
	//3. 회원가입
	//save(entity)
	
}
