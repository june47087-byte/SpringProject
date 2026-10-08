package com.mnu.sample.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tbl_notice")
@NoArgsConstructor
@Getter
@Setter
public class NoticeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_NOTICE_SEQ_IDX_GENERATOR")
	@SequenceGenerator(name="TBL_NOTICE_SEQ_IDX_GENERATOR", sequenceName = "TBL_NOTICE_SEQ_IDX", initialValue = 1, allocationSize = 1)
	private int idx;
	private String adid; //admin id 
	private String subject;
	private String contents;
	private LocalDateTime regdate = LocalDateTime.now();
	private int readcnt;
	
	@Builder
	public NoticeEntity(String adid, String subject, String contents) {
		this.adid = adid;
		this.subject = subject;
		this.contents = contents; 
	}
	
	// 관리자 id 없이 수정 
	public void noAdIdUpdate(String subject, String contents) {
		this.subject = subject;
		this.contents = contents;
	}
}
