package com.kh.second.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.second.board.model.service.BoardService;
import com.kh.second.board.model.vo.Board;



@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	//메인 게시글 btop3
	@RequestMapping(value="btop3.do", method=RequestMethod.POST)
	@ResponseBody
	public String boardTop3(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("btop3.do run....");
	      
		ArrayList<Board> list = boardService.selectTop3();
		
		JSONObject sendJSON = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(Board board : list) {
			JSONObject job = new JSONObject();
			job.put("bnum", board.getBoard_num());
			job.put("btitle", URLEncoder.encode(board.getBoard_title(), "utf-8"));
			job.put("rcount", board.getBoard_readcount());
			
			jarr.add(job);
		} 
		
		
		sendJSON.put("list", jarr);
		
		return sendJSON.toJSONString();
	}
	
	//게시글 리스트
	@RequestMapping(value = "blist.do")
	public String boardList(HttpServletRequest request, Model model) {
		int currentPage = 1;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int limit = 10;
		
		int listCount = boardService.selectListCount();
		
		ArrayList<Board> list = boardService.selectList(currentPage, limit);
		
		int maxPage = (int)((double)listCount / limit + 0.90);
		int startPage = (((int)((double)currentPage / limit + 0.90)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		
		if (maxPage < endPage) {
			endPage = maxPage;
		}
		
		if (list.size() > 0) {
			model.addAttribute("list", list);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("maxPage", maxPage);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("listCount", listCount);
			return "board/boardListView";
		} else {
			model.addAttribute("message", currentPage + "페이지에 대한 목록 조회 실패");
			return "common/error";
		}
	}
	
	//게시글 상세보기
	@RequestMapping(value="bdetail.do")
	public String boardDetail(HttpServletRequest request, Model model) {

		int currentPage = 1;
		if(request.getParameter("page") != null){
			currentPage = Integer.parseInt(request.getParameter("page"));
		}

		int bnum = Integer.parseInt(request.getParameter("bnum"));
		boardService.updateReadCount(bnum); //조회수 1 증가 처리 : update
		Board selectBoard = boardService.selectBoard(bnum); //글번호에 대한 게시글 조회 : select

		if(selectBoard != null) {
			model.addAttribute("board", selectBoard);
			model.addAttribute("currentPage", currentPage);
			return "board/boardDetailView";
		}else {
			model.addAttribute("message", bnum + "번 게시글 상세보기 조회 실패");
			return "common/error";
		}
	}
	
	//게시글 쓰기 페이지 이동
	@RequestMapping("boardWriteForm.do")
	public String moveBoardWriteForm() {
		return "board/boardWriteForm";
	}
	
	//게시글 등록
	@RequestMapping(value = "binsert.do", method = RequestMethod.POST)
	public String insertBoard(Board board, @RequestParam(name = "ofile", required = false) MultipartFile ofile, HttpServletRequest request) {
		String originalFileName = ofile.getOriginalFilename();
		String savePath = request.getSession().getServletContext().getRealPath("resources/bupfiles");
		logger.info(originalFileName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));
		
		renameFileName += "." + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
		
		
		if (!originalFileName.isEmpty()) {
			try {
				ofile.transferTo(new File(savePath + "\\" + renameFileName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			board.setBoard_original_filename(originalFileName);
			board.setBoard_rename_filename(renameFileName);
		}
		
		if (boardService.insertBoard(board) > 0) {
			return "redirect:blist.do";
		} else {
			return "common/error";
		}
	}
	
	//게시글 수정페이지이동
	@RequestMapping("bupview.do")
	public String boardUpdateView(Board board, Model model, @RequestParam("page") String page) {
		Board selectBoard = boardService.selectBoard(board.getBoard_num());
		model.addAttribute("board", selectBoard);
		model.addAttribute("page", page);
		return "board/boardUpdateView";
	}
	
	// 게시글 원글 수정하기 -- 
	   @RequestMapping(value = "boriginup.do", method = RequestMethod.POST)
	   public ModelAndView boardOriginUpdate(Board board
	         , HttpServletRequest request, ModelAndView mv,@RequestParam("page") int page
	         ,@RequestParam("ofile") String originalFileName,
	         @RequestParam("rfile") String renameFileName,
	         @RequestParam(name ="delflag", required=false) String deleteFlag,
	         @RequestParam(name ="upfile", required=false) MultipartFile file) throws IOException {
	      // 업로드되는 파일의 저장 폴더 지정하기 //db에는 저장된 파일명으로만 기록되고 파일은web에 기록됨
	      String savePath = request.getSession().getServletContext().getRealPath("/resources/bupfiles");

	      // 첨부파일 이름 바꾸기
	      String newRenameFileName = null;

	      // 바꿀 파일명에 대한 포맷 문자열 만들기 : 년월일시분초 형식으로
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	      // 바꿀 파일명 만들기 // date()~return long형 정수
	      newRenameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));
	      logger.info(originalFileName);
	      logger.info(file.getOriginalFilename());
	      // 원래 첨부파일이 있었고, 변경되지 않았을 때는 원래 꺼로 저장하겠다
	      if (/*
	          * originalFileName != null &&
	          * originalFileName.equals(file.getOriginalFilename()) && new File(savePath +
	          * "//" + renameFileName).length() == new File(savePath + "\\" +
	          * newRenameFileName) .length() &&
	          */ deleteFlag == null&&originalFileName !=null&&file.getOriginalFilename() == "" ) {

	         board.setBoard_original_filename(originalFileName);
	         board.setBoard_rename_filename(renameFileName);

	      }else if(file.getOriginalFilename() != "") { // 수정한 글에 첨부파일이 있다면
	         
	         // 업로드된 파일을 지정한 폴더로 옮기기
	         try {
	            file.transferTo(new File(savePath + "\\" + file.getOriginalFilename()));
	         } catch (IllegalStateException | IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }

	         // 서버에 새로 업로드된 파일명 저장
	         board.setBoard_original_filename(file.getOriginalFilename());

	         // 원래 파일과 새로 업로드된 파일의 이름이 같고 파일 용량도 동일하면 원래 이름 그대로 객체에 기록함
	         // 첨부파일이 없었는데 추가된 경우와 첨부파일이 있었는데 변경된 경우 둘 다 파일명 바꾸기함
	         // 첨부된 파일의 파일명 바꾸기 (추가적 작업) 저장 폴더에 같은 이름의 파일이 있을 경우를 대비하기 위함.
	         // 불러올때는 원래이름, 저장할때는 업로드 시간으로 함 /"년월일시분초.확장자" 형식으로 변경해 봄

	         // 업로드된 파일의 확장자를 추출해서, 새 파일명에 붙여줌
	         newRenameFileName += "."
	               + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

	         // 원본 파일명 rename 처리를 위해서 File 객체 만들기
	         File originFile = new File(savePath + "\\" + file.getOriginalFilename());
	         File renameFile = new File(savePath + "\\" + newRenameFileName);

	         // 이름 바꾸기 실행함
	         if (!originFile.renameTo(renameFile)) {
	            // renameTo()메소드가 실패(false)한 경우에 직접 바꾸기함
	            // 원본 파일 내용 읽어서, 복사본에 기록하고
	            // 완료되면, 원본 파일 삭제함

	            FileInputStream fin = new FileInputStream(originFile);
	            FileOutputStream fout = new FileOutputStream(renameFile);

	            int data = -1;

	            byte[] buffer = new byte[1024]; // 배열로 읽기
	            while ((data = fin.read(buffer, 0, buffer.length)) != -1) {
	               fout.write(buffer, 0, buffer.length);
	            }
	            fin.close();
	            fout.close();
	            originFile.delete(); // 원본파일 삭제

	         } // 직접이름 바꾸기

	         board.setBoard_rename_filename(newRenameFileName);

	         if (originalFileName != null) {// 원래 첨부파일이 있었다면,// 원래 첨부파일을 폴더에서 삭제함
	            new File(savePath + "\\" + renameFileName).delete();
	         } // 업로드된 새 파일이 있다면
	         
	         

	      } else if (originalFileName != null && deleteFlag != null && deleteFlag.equals("yes")) {
	         // 원래 첨부파일이 있었고, 파일삭제가 선택된 경우
	         board.setBoard_original_filename(null);
	         board.setBoard_rename_filename(null);

	         new File(savePath + "\\" + renameFileName).delete();

	      }

	      // db 수정하러 가기
	      if (boardService.updateOrigin(board) > 0) {
	         mv.setViewName("redirect:/blist.do?page=1");// 목록으로
	      } else {
	         mv.addObject("message", board.getBoard_num() + "번 공지사항 수정 실패!");
	         mv.setViewName("common/error");
	      }
	      return mv;
	   }
}
