package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("Pds")
public class PdsController {
	
	@GetMapping("pds_list")
	public String pdsList() {
		return "Pds/pds_list";
	}
	@GetMapping("pds_view")
	public String pdsview() {
		return "Pds/pds_view";
	}
	@GetMapping("pds_write")
	public String pdswrite() {
		return "Pds/pds_write";
	}
	@GetMapping("pds_delete")
	public String pdsdlete() {
		return "Pds/pds_delete";
	}
}
