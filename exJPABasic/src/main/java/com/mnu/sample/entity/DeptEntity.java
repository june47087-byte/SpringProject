package com.mnu.sample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 파라미터 없는 생성자 생성
@Entity
@Table(name="DEPT")
@Getter
public class DeptEntity {
	@Id
	private int dno;
	private String dname;
	private String loc;
	
	@Builder
	public DeptEntity(int dno, String dname, String loc) {
		this.dno = dno;
		this.dname = dname;
		this.loc = loc;
	}
}
