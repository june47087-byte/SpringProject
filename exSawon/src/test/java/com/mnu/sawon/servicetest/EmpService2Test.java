package com.mnu.sawon.servicetest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sawon.service.EmpService2;

@SpringBootTest
public class EmpService2Test {
	private static final Logger log = 
			LoggerFactory.getLogger(EmpService2Test.class);
	@Autowired
	private EmpService2 service;
	
	@Test
	public void empCountTest() {
		log.info("Service 사원 수 : " + service.empCount());
	}
}
