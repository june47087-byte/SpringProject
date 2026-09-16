package com.mnu.sample.domain;

import lombok.Data;

@Data
public class BoardDTO {
	private int idx; //고유번호
	private String pass; // 비밀번호
	private String name; // 작성자
	private String email; // 이메일
	private String regdate; // 등록일자
	private String subject; // 글제목
	private String contents; // 글 내용
	private int readcnt; // 조회수
}
