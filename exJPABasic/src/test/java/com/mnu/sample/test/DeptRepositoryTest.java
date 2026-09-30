package com.mnu.sample.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sample.dto.DeptResponseDTO;
import com.mnu.sample.entity.DeptEntity;
import com.mnu.sample.repository.DeptRepository;

@SpringBootTest
//@ActiveProfiles("test") // (application-test.yaml) H2DB를 이용한 테스트일 경우
public class DeptRepositoryTest {
	// DeptRepository 주입
	@Autowired
	private DeptRepository deptRepository;
	
	@Test
	public void insertDeptTest() {
		DeptEntity entity = DeptEntity.builder()
				.dno(13)
				.dname("인사과")
				.loc("목포")
				.build();
		DeptEntity dept = deptRepository.save(entity);
		DeptResponseDTO resDTO =  new DeptResponseDTO(entity);
		System.out.println("등록된 부서명 : " + resDTO.getDno());
	}
	
	// dno 이용한 검색
	@Test
	public void dnoSearchTest() {
		DeptEntity entity = deptRepository.findById(1)
				.orElseThrow(()->new IllegalArgumentException("dno 없음"));// null 에러 방지 기능
		DeptResponseDTO resDTO = new DeptResponseDTO(entity);
			System.out.println("검색된 부서명 : " + resDTO.getDname());
	}
	
	// 전체 검색
	@Test
	public void findAllTest() {
		List<DeptEntity> dList = deptRepository.findAll(); // 오름차순이다. asc
		//List<DeptEntity> dList = deptRepository.findAll(Sort.Direction.DESC,"dno"); // 내림차순
		for(DeptEntity entity : dList) {
			DeptResponseDTO dto = new DeptResponseDTO(entity);
			System.out.print(dto.getDno() + "  ");
			System.out.print(dto.getDname() + "  ");
			System.out.println(dto.getLoc());
			
		}
	}
/*
	// 기본키를 이용한 삭제
	@Test
	public void delete() {
		DeptEntity entity = deptRepository.findById(13)
				.orElseThrow(()->new IllegalArgumentException("등록된 id 없음"));
		deptRepository.delete(entity);
		
//		deptRepository.deleteById(13); //바로 삭제
		findAllTest();
	}
*/	
	//지역명 검색
	@Test
	public void findByLocTest() {
		List<DeptEntity> dList = deptRepository.findByLoc("목포");
		for(DeptEntity entity : dList) {
			DeptResponseDTO dto = new DeptResponseDTO(entity);
			System.out.print(dto.getDno() + " ");
			System.out.print(dto.getDname() + " ");
			System.out.println(dto.getLoc());
		}
	}
	
	// 수정 테스트
	@Test
	public void updateDeptTest() {
		DeptEntity entity = DeptEntity.builder()
				.dno(13)
				.dname("회계과")
				.loc("목포")
				.build();
		DeptEntity dept = deptRepository.save(entity);
		DeptResponseDTO resDTO =  new DeptResponseDTO(entity);
		System.out.println("등록된 부서명 : " + resDTO.getDno());
	}
	
	

}
