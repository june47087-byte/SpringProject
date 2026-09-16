package com.mnu.sample.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.mnu.sample.domain.BoardPhotoDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.service.BoardPhotoService;
import com.mnu.sample.util.PageIndex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("BoardPhoto")
public class BoardPhotoController {

	private final BoardPhotoService boardPhotoService;

	@Value("${file.upload-dir}")
	private String uploadDir;

	BoardPhotoController(BoardPhotoService boardPhotoService) {
		this.boardPhotoService = boardPhotoService;
	}

	// 썸머노트 이미지 업로드
	@PostMapping(value = "uploadImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadImageFile(@RequestParam("file") MultipartFile multipartFile) {
		JsonObject jsonObject = new JsonObject();

		String originalFileName = multipartFile.getOriginalFilename(); // 원래 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String savedFileName = UUID.randomUUID() + extension; // 저장될 파일명

		File targetFile = new File(uploadDir + savedFileName);
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일저장
			jsonObject.addProperty("url", "/upload/" + savedFileName); // contextroot + resources + 저장할 내부 폴더명
			jsonObject.addProperty("responseCode", "success");
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile); // 저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}

		// json으로 리턴하면 오류나기 때문에 string으로 리턴
		return jsonObject.toString();
	}

	@RequestMapping(value="board_list", method= {RequestMethod.GET, RequestMethod.POST })
	public String boardListPage(@RequestParam(name = "page", defaultValue = "1") int page, PageSearchDTO pgDTO, Model model) { // requestParam 을 사용하면 매개변수가 달라도 합칠 수 있다.
		int nowpage = page; // now page
		int maxlist = 10; // comment count in list
		int totpage = 1; // total page count
		int totcount = 0;// 총 글수
		if(pgDTO.getKey() != null) {
			totcount = boardPhotoService.boardCountSearch(pgDTO.getSearch(), pgDTO.getKey());
		}else {
			totcount = boardPhotoService.boardCount();
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

		List<BoardPhotoDTO> bList = null;
		String pageSkip = null;
		if(pgDTO.getKey() != null) {
			bList = boardPhotoService.boardListSearchPage(pgDTO);
			pageSkip = PageIndex.pageListHan(nowpage, totpage, "/BoardPhoto/board_list", maxlist, pgDTO.getSearch(), pgDTO.getKey());
		}else {
			bList = boardPhotoService.boardListPage(pgDTO);
			pageSkip = PageIndex.pageList(nowpage, totpage, "/BoardPhoto/board_list", maxlist);
		}

		model.addAttribute("totcount", totcount);
		model.addAttribute("totpage", totpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("bList", bList);
		model.addAttribute("pageSkip", pageSkip);
		return "BoardPhoto/board_list";
	}
	@GetMapping("board_view")
	public String boardview(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam("idx") int idx, Model model,  HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("board", boardPhotoService.boardViewModify(idx, request, response));
		return "BoardPhoto/board_view";
	}

	@GetMapping("board_write")
	public String boardwrite(@RequestParam(name = "page", defaultValue = "1") int page) {
		return "BoardPhoto/board_write";
	}
	@PostMapping("board_write")
	public String boardwrite(@RequestParam(name = "page", defaultValue = "1") int page, BoardPhotoDTO dto) {
		boardPhotoService.boardWrite(dto);
		return "redirect:/BoardPhoto/board_list?page=" + page;
	}
	// modify
	@GetMapping("board_modify")
	public String boardModify(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam("idx") int idx, Model model) {
		model.addAttribute("board", boardPhotoService.boardModify(idx));
		return "BoardPhoto/board_modify";
	}
	@PostMapping("board_modify_pro")
	public String boardModifyPro(@RequestParam(name = "page", defaultValue = "1") int page, BoardPhotoDTO dto, Model model) {
		int row = boardPhotoService.boardModifyPro(dto);
		model.addAttribute("row", row);
		model.addAttribute("idx", dto.getIdx());
		model.addAttribute("page", page);
		return "BoardPhoto/board_modify_pro";
	}
	// delete
	@GetMapping("board_delete")
	public String boardDlete(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam("idx") int idx, Model model) {
		model.addAttribute("board", boardPhotoService.boardModify(idx));
		model.addAttribute("page", page);
		return "BoardPhoto/board_delete";
	}
	@PostMapping("board_delete_pro")
	public String boardDeletePro(@RequestParam(name = "page", defaultValue = "1") int page, BoardPhotoDTO dto, Model model) {
		int row = boardPhotoService.boardDelete(dto);
		if (row == 1) {
			return "redirect:/BoardPhoto/board_list?page=" + page;
		}
		model.addAttribute("row", row);
		model.addAttribute("page", page);
		return "BoardPhoto/board_delete";
	}
}
