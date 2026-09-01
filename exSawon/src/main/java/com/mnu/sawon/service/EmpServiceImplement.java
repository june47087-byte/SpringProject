package com.mnu.sawon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sawon.domain.DeptDTO;
import com.mnu.sawon.domain.EmpDTO;
import com.mnu.sawon.mapper.EmpMapper;

@Service
public class EmpServiceImplement implements EmpService {
	@Autowired
	private EmpMapper mapper;
	//1. 사원 수 카운트		
	@Override
	public int empCount() {
		return mapper.empCount();
	}
	//2. 부서번호가 xx인 사원수
	@Override
	public int empDnoCount(int dno) {
		// TODO Auto-generated method stub
		return mapper.empDnoCount(10);
	}
	//8.  삭제 
	@Override
	public int deptDelete(int dno) {
		// TODO Auto-generated method stub
		return mapper.deptDelete(10);
	}
	//7. 수정
	@Override
	public int deptUpdate(DeptDTO dto) {
		DeptDTO Ddto = new DeptDTO();
		return mapper.deptUpdate(Ddto);
	}
	//6. 등록
	@Override
	public int deptWrite(DeptDTO dto) {
		DeptDTO ddto = new DeptDTO();
		return mapper.deptWrite(ddto);
	}
	//4. 특정부서의 사원 출력
	@Override
	public List<EmpDTO> empDnoList(int dno) {
		// TODO Auto-generated method stub
		return mapper.empDnoList(10);
	}
	//5. 특정사원의 사원번호를 가지고 정보 출력
	@Override
	public EmpDTO empEnoList(int eno) {
		// TODO Auto-generated method stub
		return mapper.empEnoList(7369);
	}
	//3. 전체목록
	@Override
	public List<EmpDTO> empList() {
		// TODO Auto-generated method stub
		return mapper.empList();
	}

	

}
