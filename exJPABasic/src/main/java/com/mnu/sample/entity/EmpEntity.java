package com.mnu.sample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name="EMP")
@Getter
public class EmpEntity {
	@Id
	private int eno;
	private String ename;
	private String job;
	private Integer manager;
	private String hiredate;
	private int salary;
	private Integer commission;
	private int dno;

	@Builder
	public EmpEntity(int eno, String ename, String job,
			Integer manager, String hiredate, int salary,
			Integer commission, int dno) {
		this.eno = eno;
		this.ename = ename;
		this.job = job;
		this.manager = manager;
		this.hiredate = hiredate;
		this.salary = salary;
		this.commission = commission;
		this.dno = dno;
	}
}
