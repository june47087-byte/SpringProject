package com.mnu.sample.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.entity.BoardEntity;

//JpaRepository 인터페이스를 상속해서 사용자 인터페이스 생성
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	//count() // 카운트
	//findAll()	//전체목록
	//save(entity)// 등록, 수정
	//findById()//기본키를 이용한 검색
	//delete()//삭제
	
	//사용자 정의 메소드(1. 쿼리 메소드 /  2. @Query 어노테이션)
	//1. 삭제(id, pass)
	@Transactional
	@Modifying
	@Query("delete from BoardEntity board where board.idx= :idx and board.pass= :pass")
	int boardDelete(@Param("idx") int idx, @Param("pass") String pass);
	
	//조회수 증가 
	@Transactional
	@Modifying
	@Query("update BoardEntity board set board.readcnt=board.readcnt+1 where board.idx=:idx")
	void boardHits(@Param("idx") int idx);
	
	//수정처리
	@Transactional
	@Modifying
	@Query("update BoardEntity board set board.subject=:subject, board.contents=:contents where board.idx= :idx and board.pass=:pass")
	int boardModify(@Param("idx") int idx, @Param("pass") String pass, @Param("name") String name,
			@Param("subject") String subject, @Param("contents") String contents);
	
	// 검색 카운트(이름, 제목, 내용)
	long countByNameContaining(String keyword);
	// (name like '%keyword%')
	long countBySubjectContaining(String keyword);
	long countByContentsContaining(String keyword);

	// 검색 목록
	List<BoardEntity> findByNameContainingOrderByIdxDesc(String keyword);
	List<BoardEntity> findBySubjectContainingOrderByIdxDesc(String keyword);
	List<BoardEntity> findByContentsContainingOrderByIdxDesc(String keyword);

	// 페이지 인덱싱
	Page<BoardEntity> findByNameContainingOrderByIdxDesc(String keyword, Pageable pageable);
	Page<BoardEntity> findBySubjectContainingOrderByIdxDesc(String keyword, Pageable pageable);
	Page<BoardEntity> findByContentsContainingOrderByIdxDesc(String keyword, Pageable pageable);

	// @query 이용한 검색 + page
	@Transactional
	@Query("select board from BoardEntity board "
			+ "where (:search='name' and board.name like %:key%) "
			+ " or (:search='subject' and board.subject like %:key%) "
			+ " or (:search='contents' and board.contents like %:key%) "
			+ " order by board.idx desc")
	Page<BoardEntity> boardListSearchPage(@Param("search") String search, 
			@Param("key") String key, Pageable pageable);
	
}
