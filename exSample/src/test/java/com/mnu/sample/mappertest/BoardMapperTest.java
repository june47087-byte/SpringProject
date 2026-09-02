package com.mnu.sample.mappertest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sample.controller.AdminController;
import com.mnu.sample.mapper.BoardMapper;

@SpringBootTest
public class BoardMapperTest {
	
	private static final Logger log = 
			LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private BoardMapper boardMapper;
/*
	@Test
	public void boardCountTest() {
		log.info("총 게시글 수 : " + boardMapper.boardCount());
	}
	@Test
	public void boardCountSearchTest() {
		String search = "name";
		String key = "김";
		log.info("검색 게시물 수 : " + boardMapper.boardCountSearch(search, key));
	}
	@Test
	public void boardListTest() {
		boardMapper.boardList().forEach(board->log.info(board.toString()));
	}
	*/
	@Test
	public void boardListSearchTest() {
		String search = "name";
		String key = "김";
		boardMapper.boardListSearch(search, key).forEach(board->log.info(board.toString()));
	}
	
}
