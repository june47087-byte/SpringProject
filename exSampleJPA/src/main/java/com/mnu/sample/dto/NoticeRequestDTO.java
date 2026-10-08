package com.mnu.sample.dto;

import com.mnu.sample.entity.NoticeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NoticeRequestDTO {
	private String adid; //admin id 
	private String subject;
	private String contents;
	
	public NoticeEntity toEntity() {
		return NoticeEntity.builder()
				.adid(adid)
				.subject(subject)
				.contents(contents)
				.build();
	}
}
