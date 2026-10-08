package com.mnu.sample.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.dto.BoardResponseDTO;
import com.mnu.sample.dto.NoticeRequestDTO;
import com.mnu.sample.dto.NoticeResponseDTO;
import com.mnu.sample.entity.BoardEntity;
import com.mnu.sample.entity.NoticeEntity;
import com.mnu.sample.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	private final NoticeRepository noticeRepository;
	
	// 등록
	@Transactional
	public long noticeWrite(NoticeRequestDTO notice) {
		return noticeRepository.count();
	}
	
	// 전체 게시글 수
	@Transactional
	public long noticeCount() {
		return noticeRepository.count();
	}
	//삭제
	@Transactional
	public void noticeDelete(int idx) {
		noticeRepository.deleteById(idx);
	}
	
	//상세보기(View)
	@Transactional
	public NoticeResponseDTO noticeView(int idx) {
		noticeRepository.noticeHits(idx);
		NoticeEntity noticeEntity = noticeRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		NoticeResponseDTO notice = new NoticeResponseDTO(noticeEntity);
		return notice;
	}
		
	// 전체 목록(검색x, 페이지 처리만)
	@Transactional
	public Page<NoticeResponseDTO> noticeList(Pageable pageable) {
		Pageable sortedPageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "idx")
		);
		
		Page<NoticeEntity> page;
		page = noticeRepository.findAll(sortedPageable);
		return page.map(NoticeResponseDTO::new);
	}
	// 전체 목록 (검색, 페이지 처리 할 경우)
	@Transactional
	public Page<NoticeResponseDTO> noticeListSearchPage(String search, String key, Pageable pageable){
		// 기존 pageable의 페이지 번호와 사이즈를 유지하면서 정렬
		Pageable sortedPageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "idx")
				);
			
		Page<NoticeEntity> page;
		if(key != null && key.equals("")) {
			// 검색
			page = noticeRepository.noticeListSearchPage(search, key, pageable);
		}else {
			// 검색 X
			page = noticeRepository.findAll(sortedPageable);
		}
		return page.map(NoticeResponseDTO::new);
	}
}
