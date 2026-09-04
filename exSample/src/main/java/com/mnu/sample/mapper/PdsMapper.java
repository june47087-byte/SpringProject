package com.mnu.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.domain.PdsDTO;

@Mapper
public interface PdsMapper {
	// 1. 전체 글수 카운트
	public int pdsCount();
	// 2. 검색 조건에 해당하는 글수
	public int pdsCountSearch(String search, String key);
	// 3-1. 전체목록 리스트(페이지 인덱싱)
	public List<PdsDTO> pdsListPage(PageSearchDTO pgDTO);
	// 4-1. 검색조건에 맞는 글 리스트(페이지 인덱싱)
	public List<PdsDTO> pdsListSearchPage(PageSearchDTO pgDTO);
	// 5. 글 등록
	public int pdsWrite(PdsDTO dto);
	// 6. 특정 글 검색(view, modify)
	public void pdsHits(int idx); // 조회수
	public PdsDTO pdsViewModify(int idx); // 상세 뷰
	// 7. 수정처리
	public int pdsModifyPro(PdsDTO dto);
	// 8. 삭제처리
	public int pdsDelete(PdsDTO dto);
}
