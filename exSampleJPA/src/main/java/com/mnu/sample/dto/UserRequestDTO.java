package com.mnu.sample.dto;

import com.mnu.sample.entity.UserEntity;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
	@NotEmpty(message="아이디는 필수 입력사항입니다.") // null, "" 허용 안 함
//	@NotBlank(message="아이디는 필수 입력사항입니다.")  null, "", " " 허용 안 함 
//	@NotNull(message="아이디는 필수 입력사항입니다.") null만 허용 안 함 
	private String userid;
	@NotEmpty(message="이름은 필수 입력사항입니다.")
	private String name;
	@NotEmpty(message="비밀번호는 필수 입력사항입니다.")
	private String passwd;
	private String tel;
	@Email(message="이메일 형식으로 입력하세요.")
	private String email;
	private String gubun;
	
	@AssertTrue(message="이메일과 전화번호 중 하나는 필수 입력값입니다.")
	public boolean isEmailOrTelPresent() {
		// 둘 다 비어 있거나 null인 경우 false 반환하여 검증 실패 처리
		if((email == null || email.trim().isEmpty()) &&
			(tel == null || tel.trim().isEmpty())) {
			return false;
		}
		return true;
	}
	
	//dto에서 필요한 부분을 entity화 
	public UserEntity toEntity() {
		return UserEntity.builder()
				.userid(userid)
				.name(name)
				.passwd(passwd)
				.tel(tel)
				.email(email)
				.gubun(gubun)
				.build();
	}
}
