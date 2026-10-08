package com.mnu.sample.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import com.mnu.sample.entity.NoticeEntity;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {
	//조회수 증가 
	@Transactional
	@Modifying
	@Query("update NoticeEntity notice set notice.readcnt=notice.readcnt+1 where notice.idx=:idx")
	void noticeHits(@Param("idx") int idx);
	
	// 쿼리를 이용한 검색과 페이지 합지것
	@Transactional
	@Query("select notice from NoticeEntity notice "
			+ "where (:search='subject' and notice.subject like %:key%) "
			+ " or (:search='contents' and notice.contents like %:key%) "
			+ " order by notice.idx desc")
	Page<NoticeEntity> noticeListSearchPage(@Param("search") String search, 
			@Param("key") String key, Pageable pageable);
}
