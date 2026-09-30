package com.mnu.sample.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.dto.EmpResponseDTO;
import com.mnu.sample.entity.EmpEntity;
import com.mnu.sample.repository.EmpRepository;

@SpringBootTest
//@ActiveProfiles("test") // (application-test.yaml) H2DB를 이용한 테스트일 경우
public class EmpRepositoryTest {
	// EmpRepository 주입
	@Autowired
	private EmpRepository empRepository;
	/*
	@Test
	public void insertEmpTest() {
		EmpEntity entity = EmpEntity.builder()
				.eno(2311)
				.ename("ALTMAN")
				.job("MANAGER")
				.manager(7839)
				.hiredate("82/01/23")
				.salary(800)
				.commission(0)
				.dno(20)
				.build();
		EmpEntity Emp = empRepository.save(entity);
		EmpResponseDTO empDTO =  new EmpResponseDTO(entity);
		System.out.println("등록된 부서명 : " + empDTO.getDno());
	}
	*/
	// eno 이용한 검색
	@Test
	public void enoSearchTest() {
		EmpEntity entity = empRepository.findById(7844)
				.orElseThrow(()->new IllegalArgumentException("eno 없음"));// null 에러 방지 기능
		EmpResponseDTO empDTO = new EmpResponseDTO(entity);
			System.out.println("검색된 사원 이름 : " + empDTO.getEname());
	}
	
	// 전체 검색
	@Test
	public void findAllTest() {
		List<EmpEntity> eList = empRepository.findAll(); // 오름차순이다. asc
		//List<EmpEntity> dList = empRepository.findAll(Sort.Direction.DESC,"dno"); // 내림차순
		for(EmpEntity entity : eList) {
			EmpResponseDTO dto = new EmpResponseDTO(entity);
			System.out.print(dto.getEno() + "  ");
			System.out.print(dto.getEname() + "  ");
			System.out.print(dto.getJob() + "  ");
			System.out.print(dto.getManager() + "  ");
			System.out.print(dto.getHiredate() + "  ");
			System.out.print(dto.getSalary() + "  ");
			System.out.print(dto.getCommission() + "  ");
			System.out.println(dto.getDno());
			
		}
	}
	
	// 기본키를 이용한 삭제
	@Test
	public void delete() {
		EmpEntity entity = empRepository.findById(2311)
				.orElseThrow(()->new IllegalArgumentException("등록된 id 없음"));
		empRepository.delete(entity);
		
//		empRepository.deleteById(13); //바로 삭제
		findAllTest();
	}
	@Test
	@Transactional
	public void empCommissionPlusTest() {
	    empRepository.empCommissionPlus(7844);
	}
}
