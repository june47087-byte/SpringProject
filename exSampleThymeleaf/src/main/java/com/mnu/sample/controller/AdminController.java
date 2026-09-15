package com.mnu.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Admin")
public class AdminController {
	@GetMapping("admin_login")
	public String adminLogin() {
		return "AdminLogin/admin_login";
	}
}
