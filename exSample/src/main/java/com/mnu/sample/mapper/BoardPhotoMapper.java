package com.mnu.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mnu.sample.domain.BoardPhotoDTO;
import com.mnu.sample.domain.PageSearchDTO;

@Mapper
public interface BoardPhotoMapper {
	// 1. 전체 글수 카운트
	public int boardCount();
	// 2. 검색 조건에 해당하는 글수
	public int boardCountSearch(String search, String key);
	// 3. 전체목록 리스트
	public List<BoardPhotoDTO> boardList();
	// 3-1. 전체목록 리스트(페이지 인덱싱)
	public List<BoardPhotoDTO> boardListPage(PageSearchDTO pageSearchDTO);
	// 4. 검색조건에 맞는 글 리스트
	public List<BoardPhotoDTO> boardListSearch(String search, String key);
	// 4-1. 검색조건에 맞는 글 리스트(페이지 인덱싱)
	public List<BoardPhotoDTO> boardListSearchPage(PageSearchDTO pageSearchDTO);
	// 5. 글 등록
	public int boardWrite(BoardPhotoDTO dto);
	// 6. 특정 글 검색(view, modify)
	public BoardPhotoDTO boardViewModify(int idx); // 상세 뷰
	// 7. 수정처리
	public int boardModifyPro(BoardPhotoDTO dto);
	// 8. 삭제처리
	public int boardDelete(BoardPhotoDTO dto);
	// 9. 인덱스용 최근거 불러오기
	public List<BoardPhotoDTO> boardTopList(@Param("count") int count);
	// 10. 조회수 증가
	public void boardHits(int idx); // 조회수
}
