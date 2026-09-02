package com.mnu.sample.servicetest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sample.controller.AdminController;
import com.mnu.sample.service.BoardService;

@SpringBootTest
public class BoardServiceTest {
	private static final Logger log = 
			LoggerFactory.getLogger(BoardServiceTest.class);
	@Autowired
	private BoardService boardService;
	
	@Test
	public void boardCount() {
//		int row = boardMapper.boardCount();
//		row++;
		log.info("총 게시물 수 : " + boardService.boardCount());
	}
}
