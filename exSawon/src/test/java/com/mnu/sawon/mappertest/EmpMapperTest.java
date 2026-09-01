package com.mnu.sawon.mappertest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sawon.mapper.EmpMapper;



@SpringBootTest
	public class EmpMapperTest {
	private static final Logger log = LoggerFactory.getLogger(EmpMapperTest.class);
	@Autowired
	private EmpMapper mapper;
	
//	//1.test(empCount())
	@Test
	public void empCounttest() {
		log.info("count1 : " + mapper.empCount());
	}
	
//	@Test
//	public void empDnoCounttest() {
//		log.info("count2 : " + mapper.empCount());
//	}
//	
//	//3. empList() 람다식 이용
//	@Test
//	public void empListTest() {
//		mapper.empList().forEach(emp->log.info(emp.toString()));
//	}
//		
//	//3. empList() 
//	public void empListTest2() {
//		List<EmpDTO> list = mapper.empList();
//	}
	
//	@Test
//	public void empDnoListTest() {
//		mapper.empDnoList(10).forEach(emp->log.info(emp.toString()));
//	}
//	
//	@Test
//	public void dmpEnoList() {
//		log.info("enoinfo : " + mapper.empEnoList(7369).toString());
//	}
//	//6.登録
//	@Test
//	public void deptWriteTest() {
//		DeptDTO dto = new DeptDTO();
//		dto.setDno(50);
//		dto.setDname("자재부");
//		dto.setLoc("목포");
//		
//		int row = mapper.deptWrite(dto);
//		log.info("결과 : " + row);
//	}
	// 수정
//	@Test
//	public void deptUpdate() {
//		DeptDTO dto = new DeptDTO();
//		dto.setDno(50);
//		dto.setDname("인사부");
//		dto.setLoc("대전");
//		int row = mapper.deptUpdate(dto);
//		log.info("수정 결과 : " + row);
//	}
	//삭제
//	@Test
//	public void deptDelete() {
//		log.info("삭제 결과 : " + mapper.deptDelete(50));
//	}
}
