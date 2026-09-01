package com.mnu.sawon.service;

import java.util.List;

import com.mnu.sawon.domain.DeptDTO;
import com.mnu.sawon.domain.EmpDTO;

public interface EmpService {
	// 1. emp 테이블이 존재하는 총 사원수(튜플) 카운트
	public int empCount();
	//2.부서 번호가 xx인 사원수 
	public int empDnoCount(int dno);
	//3. 전체 목록
	public List<EmpDTO> empList();
		
	//4. 특정부서의 사원 출력
	public List<EmpDTO> empDnoList(int dno);
		
	//5. 특정 사원의 사원번호를 가지고 정보 출력
	public EmpDTO empEnoList(int eno);
		
	//6.등록 insert, update, delete 이런 값을 전달만 하는 경우는 resultType이 없다.
	public int deptWrite(DeptDTO dto);
		
	// 7. 수정
	public int deptUpdate(DeptDTO dto);
		
	// 8. 삭제 
	public int deptDelete(int dno);
}
