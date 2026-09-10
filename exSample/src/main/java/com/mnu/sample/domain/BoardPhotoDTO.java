package com.mnu.sample.domain;

import lombok.Data;

@Data
public class BoardPhotoDTO {
	private int idx;
	private String name;
	private String pass;
	private String subject;
	private String contents;
	private String regdate;
	private int readcnt;
}
