package com.mnu.sample.domain;

import lombok.Data;

@Data
public class UserDTO {
	private String userid;
	private String name;
	private String passwd;
	private String tel;
	private String email;
	private String first_time;
	private String last_time;
	private String gubun; // 인증구분 1:전화, 2:이메일
	private Role role;
}
