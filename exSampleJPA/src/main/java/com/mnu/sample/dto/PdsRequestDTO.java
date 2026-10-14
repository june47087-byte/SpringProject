package com.mnu.sample.dto;

import com.mnu.sample.entity.PdsEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PdsRequestDTO {
	private String name;
	private String pass;
	private String email;
	private String subject;
	private String contents;
	private String filename;

	//DTO에서 필요한 부분을 entity화 시킴
	public PdsEntity toEntity() {
		return PdsEntity.builder()
				.name(name)
				.pass(pass)
				.email(email)
				.subject(subject)
				.contents(contents)
				.filename(filename)
				.build();
	}
}
