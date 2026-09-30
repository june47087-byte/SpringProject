package com.mnu.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mnu.sample.entity.EmpEntity;

public interface EmpRepository extends JpaRepository<EmpEntity, Integer> {
	// 사용자 정의 메소드 구현
		@Modifying
		@Query("update EmpEntity emp set emp.commission = emp.commission + 100 where emp.eno = :eno")
		//void empCommissionPlus(int eno);
		void empCommissionPlus(@Param("eno") int eno); // :다음은 사용자가 입력한 변수명
}
