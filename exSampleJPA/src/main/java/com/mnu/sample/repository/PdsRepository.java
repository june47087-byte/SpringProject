package com.mnu.sample.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.entity.PdsEntity;

public interface PdsRepository extends JpaRepository<PdsEntity, Integer> {

	//idx에 해당하는 글의 조회수 증가
	@Transactional
	@Modifying
	@Query("update PdsEntity pds set pds.readcnt = pds.readcnt + 1 where pds.idx = :idx")
	void pdsHits(@Param("idx") int idx);

	//삭제(idx + 비밀번호 일치)
	@Transactional
	@Modifying
	@Query("delete from PdsEntity pds where pds.idx = :idx and pds.pass = :pass")
	int pdsDelete(@Param("idx") int idx, @Param("pass") String pass);

	//@Query 이용한 검색 + Page
	@Query("select pds from PdsEntity pds "
			+ " where (:search='name' and pds.name like %:key%) "
			+ " or (:search='subject' and pds.subject like %:key%) "
			+ " or (:search='contents' and pds.contents like %:key%) "
			+ " order by pds.idx desc")
	Page<PdsEntity> pdsList(@Param("search") String search,
									@Param("key") String key, Pageable pageable);
}
