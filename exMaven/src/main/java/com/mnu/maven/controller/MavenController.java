package com.mnu.maven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MavenController {
	@GetMapping("/hello")
	public String test() {
		return "test"; // jsp 파일명
	}
	@GetMapping("/Exam")
	public String exam() {
		return "exam"; //jsp 파일명
	}
	
}
