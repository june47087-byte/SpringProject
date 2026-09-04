package com.mnu.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mnu.sample.domain.PdsDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.PdsMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PdsService {
	private final PdsMapper PdsMapper;

	public PdsService(PdsMapper PdsMapper) {
		this.PdsMapper = PdsMapper;
	}

	//1. 전체 글수 카운트
	public int pdsCount() {
//		int row = PdsMapper.pdsCount();
//		row++;
		return PdsMapper.pdsCount();
	}
	//2. 검색 조건에 해당하는 글수
	public int pdsCountSearch(String search, String key) {
		return PdsMapper.pdsCountSearch(search, key);
	}
	//3-1. 전체목록 리스트(page indexing)
	public List<PdsDTO> pdsListPage(PageSearchDTO pageSearchDTO){
		return PdsMapper.pdsListPage(pageSearchDTO);
	}
	//4. 검색조건에 맞는 글 리스트(page indexing)
	public List<PdsDTO> pdsListSearchPage(PageSearchDTO pageSearchDTO){
		return PdsMapper.pdsListSearchPage(pageSearchDTO);
	}
	//5. 글 등록
	public int pdsWrite(PdsDTO dto) {
		return PdsMapper.pdsWrite(dto);
	}
	//6. 특정 글 검색(view, modify)
	public PdsDTO pdsViewModify(int idx, HttpServletRequest request, HttpServletResponse response) {
		//쿠키설정
		boolean bool = false;
		Cookie info = null;
		Cookie[] cookies = request.getCookies();
		for(int i = 0; i < cookies.length; i++ ) {
			info = cookies[i];
			if(info.getName().equals("pdsCookie" + idx)) {
				bool= true;
				break;
			}
		}
		String str = "" + System.currentTimeMillis();
		if(!bool) {
			//create cookie
			info = new Cookie("pdsCookie" + idx, str);
			//info.setMaxAge(24*60*60); 1일
			info.setMaxAge(60*5);
			response.addCookie(info);
			PdsMapper.pdsHits(idx);
		}
		PdsMapper.pdsHits(idx);
		PdsDTO pDTO = PdsMapper.pdsViewModify(idx);
		pDTO.setContents(pDTO.getContents().replace("\n", "<br>"));
		return pDTO;
	}
	//7. 수정처리(폼)
	public PdsDTO pdsModify(int idx) {
		return PdsMapper.pdsViewModify(idx);
	}
	//7. 수정처리
	public int pdsModifyPro(PdsDTO dto) {
		return PdsMapper.pdsModifyPro(dto);
	}
	//8. 삭제처리
	public int pdsDelete(PdsDTO dto) {
		return PdsMapper.pdsDelete(dto);
	}
}
