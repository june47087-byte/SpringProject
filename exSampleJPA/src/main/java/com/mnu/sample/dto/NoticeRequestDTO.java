package com.mnu.sample.dto;

import com.mnu.sample.entity.NoticeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class NoticeRequestDTO {
	private String adid;
	private String subject;
	private String contents;

	//DTO에서 필요한 부분을 entity화 시킴
	public NoticeEntity toEntity() {
		return NoticeEntity.builder()
				.adid(adid)
				.subject(subject)
				.contents(contents)
				.build();
	}
}
