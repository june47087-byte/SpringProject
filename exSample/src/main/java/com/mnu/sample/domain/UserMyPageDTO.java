package com.mnu.sample.domain;

import lombok.Data;

@Data
public class UserMyPageDTO {
	private int userid;
	private int totsubject;
	private int pdssubject;
	private int boardsubject;
	private int boardphotosubject;
	private int totcomment;
	private String email;
	private String passwd;
	private String name;
	private String tel;
}
