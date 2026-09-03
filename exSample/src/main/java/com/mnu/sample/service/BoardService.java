package com.mnu.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.BoardMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class BoardService {
	private final BoardMapper boardMapper;

	public BoardService(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	//1. 전체 글수 카운트
	public int boardCount() {
//		int row = boardMapper.boardCount();
//		row++;
		return boardMapper.boardCount();
	}
	//2. 검색 조건에 해당하는 글수
	public int boardCountSearch(String search, String key) {
		return boardMapper.boardCountSearch(search, key);
	}
	//3. 전체목록 리스트
	public List<BoardDTO> boardList(){
		return boardMapper.boardList();
	}
	//3-1. 전체목록 리스트(page indexing)
	public List<BoardDTO> boardListPage(PageSearchDTO pageSearchDTO){
		return boardMapper.boardListPage(pageSearchDTO);
	}
	//4. 검색조건에 맞는 글 리스트
	public List<BoardDTO> boardListSearch(String search, String key){
		return boardMapper.boardListSearch(search, key);
	}
	//4. 검색조건에 맞는 글 리스트(page indexing)
	public List<BoardDTO> boardListSearchPage(PageSearchDTO pageSearchDTO){
		return boardMapper.boardListSearchPage(pageSearchDTO);
	}
	//5. 글 등록
	public int boardWrite(BoardDTO dto) {
		return boardMapper.boardWrite(dto);
	}
	//6. 특정 글 검색(view, modify)
	public BoardDTO boardViewModify(int idx, HttpServletRequest request, HttpServletResponse response) {
		//쿠키설정
		boolean bool = false;
		Cookie info = null;
		Cookie[] cookies = request.getCookies();
		for(int i = 0; i < cookies.length; i++ ) {
			info = cookies[i];
			if(info.getName().equals("boardCookie" + idx)) {
				bool= true;
				break;
			}
		}
		String str = "" + System.currentTimeMillis();
		if(!bool) {
			//create cookie
			info = new Cookie("boardCookie" + idx, str);
			//info.setMaxAge(24*60*60); 1일
			info.setMaxAge(60*5);
			response.addCookie(info);
			boardMapper.boardHits(idx);
		}
		boardMapper.boardHits(idx);
		BoardDTO bDTO = boardMapper.boardViewModify(idx);
		bDTO.setContents(bDTO.getContents().replace("\n", "<br>"));
		return bDTO;
	}
	//7. 수정처리(폼)
	public BoardDTO boardModify(int idx) {
		return boardMapper.boardViewModify(idx);
	}
	//7. 수정처리
	public int boardModifyPro(BoardDTO dto) {
		return boardMapper.boardModifyPro(dto);
	}
	//8. 삭제처리
	public int boardDelete(BoardDTO dto) {
		return boardMapper.boardDelete(dto);
	}
}
