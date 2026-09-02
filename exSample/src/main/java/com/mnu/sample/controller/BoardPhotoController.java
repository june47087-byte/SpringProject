package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("BoardPhoto")
public class BoardPhotoController {
	
	
	@GetMapping("boardphoto_list")
	public String boardphotoList() {
		return "BoardPhoto/board_list";
	}
	@GetMapping("BoardPhoto_view")
	public String BoardPhotoview() {
		return "BoardPhoto/board_view";
	}
	@GetMapping("BoardPhoto_write")
	public String BoardPhotowrite() {
		return "BoardPhoto/board_write";
	}
	@GetMapping("boardphoto_delete")
	public String BoardPhotodlete() {
		return "BoardPhoto/board_delete";
	}
}
