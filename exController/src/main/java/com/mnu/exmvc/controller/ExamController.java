package com.mnu.exmvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mnu.exmvc.domain.DeptDTO;

@Controller
@RequestMapping("")
public class ExamController {
	// 로그 출력
	private static final Logger log = 
			LoggerFactory.getLogger(ExamController.class);
	// 반환값이 없으면 매핑값이 jsp가 되고 리턴값이 있으면 리턴값이 jsp파일이 된다.
	@GetMapping("index")
	public void mainIndex() {
		log.info("Call : index");
	}
	@GetMapping("index1")
	public void mainIndex1() {
		log.info("Call : index1");
	}
	@GetMapping("index2")
	public String mainIndex2() {
		log.info("Call : index2");
		return "/Exam/test";
	}
	//파라미터 수집(파라미터로 사용된 변수와 전달되든 변수가 같을 경우)
	@GetMapping("ex01")
	public void ex01(String name, int idx) {
		log.info("name : " + name);
		log.info("idx : " + idx);
	}
	//파라미터 수집(파라미터로 사용된 변수와 전달되든 변수가 다른 경우)
	@GetMapping("ex02")
	public void ex02(@RequestParam("name") String na, @RequestParam("idx") int no) {
		log.info("name : " + na);
		log.info("idx : " + no);
	}
	//파라미터가 자동 수집(DTO, VO)
	@GetMapping("ex03")
	public void ex03(DeptDTO dto) {
		log.info("dto : " + dto);
		log.info("dno : " + dto.getDno());
		log.info("dname : " + dto.getDname());
		log.info("loc : " + dto.getLoc());
	}
	//리스트
	@GetMapping("ex04")
	public void ex04(@RequestParam("data") ArrayList<String> list) {
		log.info("list : " + list);
	}
	//전달(views)--> 스프링에은 전달자(Model)를 사용한다. RequestSetAtrribute("", ); 같은거다
	@GetMapping("trans01")
	public String trans01(Model model) {
		model.addAttribute("name", "홍길동");
		return "exam01";
	}
	// 메소드 안에 리스트 사용한다면
	@GetMapping("trans02")
	public String trans02(Model model) {
		List<String> list = new ArrayList<String>();
		list.add("김학생");list.add("이학생");
		list.add("이학생");list.add("나학생");
		list.add("박학생");list.add("최학생");
		model.addAttribute("list", list);
		return "exam02";
	}
	// @ModelAttribute 전달자 / DTO 전달자 -> 전달되면 view에서는 첫글자를 소문자로 사용.
	
	@GetMapping("trans03")
	public String trans03(DeptDTO dto, int page) {
		
		return "exam03";
	}
	@GetMapping("trans04")
	public String trans04(DeptDTO dto, @ModelAttribute("page") int pa) {
		return "exam03";
	}
	//resonse.sendRedirect==> RedirectAttributes 1회성이 필요할 때 사용한다.
	//RedirectAttributes -> 1. addAttribute() : 화면에 보임, 주로 url 이용 방식
	//  				 -> 2. addFlashAttribute() : 화면에 안 보임, 주소 세션을 이용
	@GetMapping("trans05")
	public String exam05(RedirectAttributes rttr) {
//		rttr.addAttribute("page", 10); 
		rttr.addFlashAttribute("page", 10);
		return "redirect:trans04"; // redirect:하면 controller url로 가는거다.
//		return "redirect:trans04?page=10"; // 이런 방식도 있다
	}
	// 컨트롤러의 반환타입이 void, String => jsp로 이동
	// VO, DTO 주로 JSON타입의 데이터를 만들어 반활할 경우
	@GetMapping("trans06")
	public @ResponseBody DeptDTO trans06() {
		DeptDTO dto = new DeptDTO();
		dto.setDno(10);
		dto.setDname("영업부");
		dto.setLoc("대전");
		return dto;
	}
	
	
	
	
	
	
	
	
	
	
}
