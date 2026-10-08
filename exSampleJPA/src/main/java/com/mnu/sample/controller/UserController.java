package com.mnu.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnu.sample.dto.UserRequestDTO;
import com.mnu.sample.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("User")
public class UserController {
	private static final Logger log =
			LoggerFactory.getLogger(BoardController.class);
	private final UserService userService;
	
	// 회원가입 폼
	@GetMapping("user_insert")
	public String userInsert(Model model) {
		log.info("user call : user_insert");
		model.addAttribute("userRequestDTO", new UserRequestDTO());
		return "/User/user_insert";
	}
	// ID 중복 검사
	@ResponseBody
	@PostMapping("user_idCheck")
	public String useridCheck(@RequestParam("userid") String userid) {
		boolean exists = userService.userIdCheck(userid);
		return String.valueOf(exists);
	}
	
	// 보안인증(핸드폰)
	
	// 보안인증(email)
	
	// 회원가입 처리
	@PostMapping("user_insert")
	public String userInsertPro(@Valid UserRequestDTO userRequestDTO,
					BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "User/user_insert";
		}
		
		return "/";
	}
	// 로그인 폼
	@GetMapping("user_login")
	public String userLogin() {
		log.info("user call : user_login");
		return "/User/user_login";
	}
}
