package com.mnu.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mnu.sample.domain.BoardPhotoDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.BoardPhotoMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class BoardPhotoService {
	private final BoardPhotoMapper BoardPhotoMapper;

	public BoardPhotoService(BoardPhotoMapper BoardPhotoMapper) {
		this.BoardPhotoMapper = BoardPhotoMapper;
	}

	//1. 전체 글수 카운트
	public int boardCount() {
//		int row = BoardPhotoMapper.boardCount();
//		row++;
		return BoardPhotoMapper.boardCount();
	}
	//2. 검색 조건에 해당하는 글수
	public int boardCountSearch(String search, String key) {
		return BoardPhotoMapper.boardCountSearch(search, key);
	}
	//3. 전체목록 리스트
	public List<BoardPhotoDTO> boardList(){
		return BoardPhotoMapper.boardList();
	}
	//3-1. 전체목록 리스트(page indexing)
	public List<BoardPhotoDTO> boardListPage(PageSearchDTO pageSearchDTO){
		return BoardPhotoMapper.boardListPage(pageSearchDTO);
	}
	//4. 검색조건에 맞는 글 리스트
	public List<BoardPhotoDTO> boardListSearch(String search, String key){
		return BoardPhotoMapper.boardListSearch(search, key);
	}
	//4. 검색조건에 맞는 글 리스트(page indexing)
	public List<BoardPhotoDTO> boardListSearchPage(PageSearchDTO pageSearchDTO){
		return BoardPhotoMapper.boardListSearchPage(pageSearchDTO);
	}
	//5. 글 등록
	public int boardWrite(BoardPhotoDTO dto) {
		return BoardPhotoMapper.boardWrite(dto);
	}
	//6. 특정 글 검색(view, modify)
	public BoardPhotoDTO boardViewModify(int idx, HttpServletRequest request, HttpServletResponse response) {
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
			BoardPhotoMapper.boardHits(idx);
		}
		BoardPhotoMapper.boardHits(idx);
		BoardPhotoDTO bDTO = BoardPhotoMapper.boardViewModify(idx);
		bDTO.setContents(bDTO.getContents().replace("\n", "<br>"));
		return bDTO;
	}
	//7. 수정처리(폼)
	public BoardPhotoDTO boardModify(int idx) {
		return BoardPhotoMapper.boardViewModify(idx);
	}
	//7. 수정처리
	public int boardModifyPro(BoardPhotoDTO dto) {
		return BoardPhotoMapper.boardModifyPro(dto);
	}
	//8. 삭제처리
	public int boardDelete(BoardPhotoDTO dto) {
		return BoardPhotoMapper.boardDelete(dto);
	}
	//9. idx 내림차순 최근 N건(인덱스용)
	public List<BoardPhotoDTO> boardTopList(int count) {
		return BoardPhotoMapper.boardTopList(count);
	}
}
