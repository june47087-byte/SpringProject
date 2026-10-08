package com.mnu.sample.dto;

import com.mnu.sample.entity.UserEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDTO {
	private String userid;
	private String name;
	private String passwd;
	private String tel;
	private String email;
	private Role role;
	private String gubun;
	
	public UserResponseDTO(UserEntity entity) {
		this.userid = entity.getUserid();
		this.name = entity.getName();
		this.passwd = entity.getPasswd();
		this.tel = entity.getTel();
		this.email = entity.getEmail();
		this.role= entity.getRole();
		this.gubun = entity.getGubun();
	}
}
