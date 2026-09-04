package com.mnu.sample.controller;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.domain.PdsDTO;
import com.mnu.sample.service.PdsService;
import com.mnu.sample.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("Pds")
public class PdsController {
	private final PdsService pdsService;

	PdsController(PdsService pdsService) {
		this.pdsService = pdsService;
	}	
	@RequestMapping(value="pds_list", method= {RequestMethod.GET, RequestMethod.POST })
	public String pdsList(@RequestParam(defaultValue = "1") int page, PageSearchDTO pgDTO, Model model) {
		int nowpage = page; // now page
		int maxlist = 10; // comment count in list
		int totpage = 1; // total page count
		int totcount = 0;// 총 글수
		if(pgDTO.getKey() != null) {
			totcount = pdsService.pdsCountSearch(pgDTO.getSearch(), pgDTO.getKey());
		}else {
			totcount = pdsService.pdsCount();
		}
		// count total page
		if(totcount % maxlist == 0) 
			totpage = totcount / maxlist; 
		else
			totpage = totcount / maxlist + 1; 
		// pagenumber check that user selected page	
		int offset = (nowpage - 1) * maxlist;
		// use contents idx print
		int listcount = totcount - ((nowpage - 1) * maxlist);
		
		pgDTO.setOffset(offset);
		pgDTO.setMaxlist(maxlist);
		
		List<PdsDTO> bList = null;
		String pageSkip = null;
		if(pgDTO.getKey() != null) {
			bList = pdsService.pdsListSearchPage(pgDTO);
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "/Pds/pds_list", maxlist, pgDTO.getSearch(), pgDTO.getKey());
		}else {
			bList = pdsService.pdsListPage(pgDTO);
			pageSkip = PageIndex.pageList(nowpage, totpage, "/Pds/pds_list", maxlist);
		}
		
		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("bList", bList);
		model.addAttribute("pageSkip", pageSkip);
		return "Pds/pds_list";
	}
		
	@GetMapping("pds_view")
	public String pdsView(@RequestParam(defaultValue = "1") int page, @RequestParam("idx") int idx, Model model,  HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("pds", pdsService.pdsViewModify(idx, request, response));
		return "Pds/pds_view";
	}
	@GetMapping("pds_write")
	public String pdsWrite(@RequestParam(defaultValue = "1") int page) {
		return "Pds/pds_write";
	}
	@PostMapping("pds_write")
	public String pdsWritePro(@RequestParam(defaultValue = "1") int page, MultipartHttpServletRequest request ) {
		PdsDTO pDTO = new PdsDTO();
		pDTO.setName(request.getParameter("name"));
		pDTO.setEmail(request.getParameter("email"));
		pDTO.setSubject(request.getParameter("subject"));
		pDTO.setContents(request.getParameter("contents"));
		pDTO.setPass(request.getParameter("pass"));
		MultipartFile mf = request.getFile("filename");
		// 저장경로 설정
		String path = request.getServletContext().getRealPath("/upload/");
		// 파일이름 추출
		String fileName = mf.getOriginalFilename();
		pDTO.setFilename(fileName);
		
		//실제 파이 저장
		File file = new File(path+fileName);
		try {
			mf.transferTo(file);
		}catch(Exception e) {
			e.printStackTrace();
		}
		pdsService.pdsWrite(pDTO);
		return "redirect:/Pds/pds_list?page=" + page;
	}
	// modify
	@GetMapping("pds_modify")
	public String pdsModify(@RequestParam(defaultValue= "1") int page, @RequestParam("idx") int idx, Model model) {
		model.addAttribute("pds", pdsService.pdsModify(idx));
		return "Pds/pds_modify";
	}
	@PostMapping("pds_modify_pro")
	public String boardModifyPro(@RequestParam(defaultValue= "1") int page, MultipartHttpServletRequest request, Model model) {
		PdsDTO dto = new PdsDTO();
		dto.setIdx(Integer.parseInt(request.getParameter("idx")));
		dto.setName(request.getParameter("name"));
		dto.setEmail(request.getParameter("email"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContents(request.getParameter("contents"));
		dto.setPass(request.getParameter("pass"));

		MultipartFile mf = request.getFile("filename");
		if (mf != null && !mf.isEmpty()) {
			String path = request.getServletContext().getRealPath("/upload/");
			String fileName = mf.getOriginalFilename();
			dto.setFilename(fileName);
			File file = new File(path + fileName);
			try {
				mf.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			dto.setFilename(pdsService.pdsModify(dto.getIdx()).getFilename());
		}

		int row = pdsService.pdsModifyPro(dto);
		model.addAttribute("row", row);
		model.addAttribute("idx", dto.getIdx());
		model.addAttribute("page", page);
		return "Pds/pds_modify_pro";
	}
	@GetMapping("pds_delete")
	public String pdsDelete(@RequestParam(defaultValue= "1") int page, @RequestParam("idx") int idx, Model model) {
		model.addAttribute("pds", pdsService.pdsModify(idx));
		model.addAttribute("page", page);
		return "Pds/pds_delete";
	}
	@PostMapping("pds_delete_pro")
	public String pdsDeletePro(@RequestParam(defaultValue= "1") int page, PdsDTO dto, Model model) {
		int row = pdsService.pdsDelete(dto);
		if (row == 1) {
			return "redirect:/Pds/pds_list?page=" + page;
		}
		model.addAttribute("row", row);
		model.addAttribute("page", page);
		return "Pds/pds_delete";
	}
	
	// 다운로드
	@GetMapping("down_load")
	public ResponseEntity<Resource> download(@RequestParam("idx") int idx, HttpServletRequest request) {
		PdsDTO dto = pdsService.pdsModify(idx);
		String fileName = dto.getFilename();
		String path = request.getServletContext().getRealPath("/upload/");
		File file = new File(path + fileName);
		Resource resource = new FileSystemResource(file);
		if (!resource.exists()) {
			return ResponseEntity.notFound().build();
		}

		String encodedName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedName + "\"")
				.body(resource);
	}
}
