package com.mnu.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mnu.sample.domain.NoticeDTO;
import com.mnu.sample.domain.PageSearchDTO;
@Mapper
public interface NoticeMapper {
	//1. 전체 공지사항 카운트
	public int noticeCount();
	//2. 검색조건에 맞는 공지사항 카운트
	public int noticeSearchCount(PageSearchDTO pageSearchDTO);
	//3. 공지사항 목록(검색+page) 겸용
	public List<NoticeDTO> noticeList(PageSearchDTO pageSearchDTO);
	//4. idx에 해당하는 글 목록(view, modify) 사용
	public void noticeHits(int idx); // 조회수
	public NoticeDTO noticeSelect(int idx);
	//5. 공지사항 등록(write)
	public int noticeWrite(NoticeDTO noticeDTO);
	//6. 공지사항 수정(modify)
	public int noticeModify(NoticeDTO noticeDTO);
	//7. 공지사항 삭제
	public int noticeDelete(int idx);
	//8. 인덱스용 최근거 불러오기
	public List<NoticeDTO> noticeTopList(@Param("count") int count);
}
