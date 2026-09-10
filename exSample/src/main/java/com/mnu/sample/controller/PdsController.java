package com.mnu.sample.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

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

	@Value("${file.upload-dir}")
	private String uploadDir;

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
		String path = uploadDir;
		// 파일이름 추출
		String fileName = mf.getOriginalFilename();
		pDTO.setFilename(fileName);
		
		//실제 파일 저장
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
			String path = uploadDir;
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
	public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String filename) {
        try {
            // 2. 보안을 위해 상위 디렉토리 접근 차단(.normalize()) 및 경로 병합
            Path path = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(path.toUri());

            // 3. 파일 존재 및 읽기 가능 여부 체크
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다: " + filename);
            }

            // 4. 한글 파일명 깨짐 방지 인코딩
            String encodedFilename = UriUtils.encode(filename, StandardCharsets.UTF_8);
            
            // 5. 다운로드 창을 띄우기 위한 Content-Disposition 설정
            String contentDisposition = "attachment; filename=\"" + encodedFilename + "\"";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 경로 오류가 발생했습니다.");
        }
    }
}