package com.mnu.sample.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnu.sample.dto.BoardRequestDTO;
import com.mnu.sample.dto.BoardResponseDTO;
import com.mnu.sample.entity.BoardEntity;
import com.mnu.sample.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //Bean 주입
public class BoardService {
	private final BoardRepository boardRepository;
	
	// 등록 처리
	@Transactional
	public int boardWrite(BoardRequestDTO board) {
		return boardRepository.save(board.toEntity()).getIdx();
		//등록후 등록된 idx 반환
	}
	
	// 카운트(전체 게시글 수)
	@Transactional
	public long boardCount() {
		return boardRepository.count();
	}
/*	
	// 전체 목록
	@Transactional
	public List<BoardResponseDTO> boardList(){
		return boardRepository.findAll()
				.stream()
				// Board 엔티티의 getIdx()를 기준으로 내림차순(reversed) 정렬
	            .sorted(Comparator.comparing(BoardEntity::getIdx).reversed()) 
				.map(BoardResponseDTO::new)
				//.collect(Collectors.toList());
				.toList();//JDK 16이상
		// BoardRepository결과로 넘어온 BoardEntity의 Stream을 map을 통해 BoardReponseDto로 변환 
        //   -> List로 반환하는 메서드
	}
*/
	
	// 전체 목록 (검색 X, 페이지 처리 할 경우)
	@Transactional
	public Page<BoardResponseDTO> boardList(Pageable pageable){
		// 기존 pageable의 페이지 번호와 사이즈를 유지하면서 정렬
		Pageable sortedPageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "idx")
		);
		
		Page<BoardEntity> page;
		page = boardRepository.findAll(sortedPageable);
		return page.map(BoardResponseDTO::new);
	}

	// 전체 목록 (검색, 페이지 처리 할 경우)
	@Transactional
	public Page<BoardResponseDTO> boardListSearchPage(String search, String key, Pageable pageable){
		// 기존 pageable의 페이지 번호와 사이즈를 유지하면서 정렬
		Pageable sortedPageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "idx")
				);
		
		Page<BoardEntity> page;
		if(key != null && key.equals("")) {
			// 검색
			page = boardRepository.boardListSearchPage(search, key, pageable);
		}else {
			// 검색 X
			page = boardRepository.findAll(sortedPageable);
		}
		return page.map(BoardResponseDTO::new);
	}
	

	//상세보기(View)
	@Transactional
	public BoardResponseDTO boardView(int idx) {
		// 조회수 증가
		boardRepository.boardHits(idx);
		BoardEntity boardEntity = boardRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		BoardResponseDTO board = new BoardResponseDTO(boardEntity);
		return board;
	}
	
	//상세보기(Modify)
	@Transactional
	public BoardResponseDTO boardModify(int idx) {
		BoardEntity boardEntity = boardRepository.findById(idx)
				.orElseThrow(()->new IllegalArgumentException("idx 없음"));
		BoardResponseDTO board = new BoardResponseDTO(boardEntity);
		return board;
	}
	
	//수정처리
	@Transactional
	public int boardModifyPro(int idx, BoardRequestDTO board) {
		return boardRepository.boardModify(idx, board.getPass(), board.getName(), board.getSubject(), board.getContents());
	}
	
	//삭제
	@Transactional
	public int boardDelete(int idx, String pass) {
		return boardRepository.boardDelete(idx, pass);
	}
	
	// 조건에 맞는 글수 카운트
	@Transactional
	public long boardCountSearch(String search, String key) {
		switch(search) {
		case "name":
			return boardRepository.countByNameContaining(key);
		case "subject":
			return boardRepository.countBySubjectContaining(key);
		case "contents":
			return boardRepository.countByContentsContaining(key);
		default:
			return 0;
		}
	}
	
	// 조건에 맞는 게시글 목록 (페이지 처리)
	@Transactional
	public Page<BoardResponseDTO> boardListSearch(String search, String key, Pageable pageable) {
		Page<BoardEntity> page;
		switch(search) {
		case "name":
			page = boardRepository.findByNameContainingOrderByIdxDesc(key, pageable);
			break;
		case "subject":
			page = boardRepository.findBySubjectContainingOrderByIdxDesc(key, pageable);
			break;
		case "contents":
			page = boardRepository.findByContentsContainingOrderByIdxDesc(key, pageable);
			break;
		default:
			page = Page.empty(pageable);
		}
		return page.map(BoardResponseDTO::new);
	}
}
