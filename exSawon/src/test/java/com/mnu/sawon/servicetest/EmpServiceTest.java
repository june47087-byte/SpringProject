package com.mnu.sawon.servicetest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sawon.service.EmpService;
import com.mnu.sawon.service.EmpServiceImplement;

@SpringBootTest
public class EmpServiceTest {
	private static final Logger log = 
			LoggerFactory.getLogger(EmpServiceImplement.class);
	@Autowired
	private EmpService service;
	
	@Test
	public void empCountTest() {
		log.info("Service 사원 수 : " + service.empCount());
	}
}
