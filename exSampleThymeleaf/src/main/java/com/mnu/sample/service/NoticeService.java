package com.mnu.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mnu.sample.domain.NoticeDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.NoticeMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class NoticeService {
	private final NoticeMapper NoticeMapper;

	public NoticeService(NoticeMapper NoticeMapper) {
		this.NoticeMapper = NoticeMapper;
	}

	//1. 전체 글수 카운트
	public int NoticeCount() {
		return NoticeMapper.noticeCount();
	}
	//2. 검색 조건에 해당하는 글수
	public int NoticeCountSearch(PageSearchDTO pageSearchDTO) {
		return NoticeMapper.noticeSearchCount(pageSearchDTO);
	}
	//3-1. 전체목록 리스트(page indexing)
	public List<NoticeDTO> NoticeList(PageSearchDTO pageSearchDTO){
		return NoticeMapper.noticeList(pageSearchDTO);
	}
	//4-1. idx에 해당하는 글 목록(View, modify) 사용
	public void noticeHits(int idx) {
		NoticeMapper.noticeHits(idx);
	}
	//4-2. idx에 해당하는 글 목록(View, modify) 사용
	public NoticeDTO noticeSelect(int idx) {
		return NoticeMapper.noticeSelect(idx);
	}
	//5. 글 등록
	public int NoticeWrite(NoticeDTO dto) {
		return NoticeMapper.noticeWrite(dto);
	}
	//6. 특정 글 검색(view, modify)
	public NoticeDTO NoticeViewModify(int idx, HttpServletRequest request, HttpServletResponse response) {
		//쿠키설정
		boolean bool = false;
		Cookie info = null;
		Cookie[] cookies = request.getCookies();
		for(int i = 0; i < cookies.length; i++ ) {
			info = cookies[i];
			if(info.getName().equals("noticeCookie" + idx)) {
				bool= true;
				break;
			}
		}
		String str = "" + System.currentTimeMillis();
		if(!bool) {
			//create cookie
			info = new Cookie("noticeCookie" + idx, str);
			//info.setMaxAge(24*60*60); 1일
			info.setMaxAge(60*5);
			response.addCookie(info);
			NoticeMapper.noticeHits(idx);
		}
		NoticeMapper.noticeHits(idx);
		NoticeDTO nDTO = NoticeMapper.noticeSelect(idx);
		nDTO.setContents(nDTO.getContents().replace("\n", "<br>"));
		return nDTO;
	}
	//7. 수정처리(폼)
	public NoticeDTO NoticeModify(int idx) {
		return NoticeMapper.noticeSelect(idx);
	}
	//7. 수정처리
	public int NoticeModifyPro(NoticeDTO dto) {
		return NoticeMapper.noticeModify(dto);
	}
	//8. 삭제처리
	public int NoticeDelete(int idx) {
		return NoticeMapper.noticeDelete(idx);
	}
	//9. idx 내림차순 최근 N건(인덱스용)
	public List<NoticeDTO> noticeTopList(int count) {
		return NoticeMapper.noticeTopList(count);
	}
}
