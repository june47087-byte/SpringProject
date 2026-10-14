package com.mnu.sample.dto;

import java.time.LocalDateTime;

import com.mnu.sample.entity.PdsEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PdsResponseDTO {
	private int idx;
	private String name;
	private String pass;
	private String email;
	private String subject;
	private String contents;
	private LocalDateTime regdate;
	private int readcnt;
	private String filename;

	//entity -> dto
	public PdsResponseDTO(PdsEntity entity) {
		this.idx = entity.getIdx();
		this.name = entity.getName();
		this.pass = entity.getPass();
		this.email = entity.getEmail();
		this.subject = entity.getSubject();
		this.contents = entity.getContents();
		this.regdate = entity.getRegdate();
		this.readcnt = entity.getReadcnt();
		this.filename = entity.getFilename();
	}
}
