package com.mnu.sample.mappertest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mnu.sample.controller.AdminController;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.PdsMapper;

@SpringBootTest
public class PdsMapperTest {
	
	private static final Logger log = 
			LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private PdsMapper pdsMapper;

	@Test
	public void pdsCountTest() {
		log.info("총 게시글 수 : " + pdsMapper.pdsCount());
	}
	@Test
	public void pdsCountSearchTest() {
		String search = "name";
		String key = "김";
		log.info("검색 게시물 수 : " + pdsMapper.pdsCountSearch(search, key));
	}
	
	@Test
	public void pdsListPageTest() {
		PageSearchDTO dto = new PageSearchDTO();
		dto.setOffset(0);
		dto.setMaxlist(10);
		pdsMapper.pdsListPage(dto).forEach(pds->log.info(pds.toString()));
		
	}
	
	@Test
	public void pdsListSearchPageTest() {
		PageSearchDTO dto = new PageSearchDTO();
		dto.setOffset(0);
		dto.setMaxlist(10);
		dto.setKey("김");
		dto.setSearch("name");
		pdsMapper.pdsListPage(dto).forEach(pds->log.info(pds.toString()));
		
	}
	
}
