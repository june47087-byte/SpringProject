package com.mnu.sawon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sawon.mapper.EmpMapper;

@Service
public class EmpService2 {
	@Autowired
	private EmpMapper mapper;
	// 1. emp 테이블이 존재하는 총 사원수(튜플) 카운트
	public int empCount() {
		return mapper.empCount();
	}
}
