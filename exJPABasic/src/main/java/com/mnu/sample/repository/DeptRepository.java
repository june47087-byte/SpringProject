package com.mnu.sample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mnu.sample.entity.DeptEntity;

public interface DeptRepository extends JpaRepository<DeptEntity, Integer> {
	// T: entity 의미, ID: 기본키(객체) 의미
	// 사용자 정의 메소드 생성(따로 생성 안 해도 기본적 CRUD는 완성되어 있다.)
	// 기본적 CRUD(생성, 조회, 수정, 삭제), 페이징, 정렬 및 배치 처리를 위한 다양한 메소드를 즉시 사용
	// 개수 조회는 JpaRepository가 기본 제공하는 count()를 사용하면 된다.
	// 지역명을 이용한 검색
	List<DeptEntity> findByLoc(String loc);
	// 부서명을 이용한 검색
	List<DeptEntity> findByDname(String name);
	
}
