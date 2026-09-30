package com.mnu.sample.dto;

import com.mnu.sample.entity.EmpEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmpRequestDTO {
	private int eno;
	private String ename;
	private String job;
	private Integer manager;
	private String hiredate;
	private int salary;
	private Integer commission;
	private int dno;
	
	public EmpEntity toEntity() {
		return EmpEntity.builder()
				.eno(eno)
				.ename(ename)
				.job(job)
				.manager(manager)
				.hiredate(hiredate)
				.salary(salary)
				.commission(commission)
				.dno(dno)
				.build();
	}
}
