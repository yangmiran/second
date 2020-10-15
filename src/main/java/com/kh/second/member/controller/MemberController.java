package com.kh.second.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.kh.second.member.model.service.MemberService;
import com.kh.second.member.model.vo.Member;

@Controller
public class MemberController {
	/* HomeController.java 파일에서 복사해옴 */
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//DI : 객체 생성은 스프링이 함 (자동 의존성 주입됨)
	@Autowired
	private MemberService memberService;
	
	//로그인
	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public String loginMethod(Member member, HttpSession session, SessionStatus status, Model model) {
//		String userid = request.getParameter("userid");
//		String userpwd = request.getParameter("userpwd");
//		Member member = new Member();
//		member.setUserid(userid);
//		member.setUserpwd(userpwd);
		
		logger.info("login.do : " + member.getUserid() + ", " + member.getUserpwd());
		
		Member loginMember = memberService.selectLogin(member);
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			status.setComplete(); //요청 성공, 200 전송
			return "common/main";
		}else {
			model.addAttribute("message", "로그인 실패!");
			return "common/error";
		}
	}
	
	@RequestMapping("loginPage.do")
	public String moveLoginPage() {
		return "member/loginPage";
	}
	
	@RequestMapping("enrollPage.do")
	public String moveEnrollPage() {
		return "member/enrollPage";
	}
	
	//로그아웃
	@RequestMapping("logout.do")
	public String logoutMethod(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
			return "common/main";
		}else {
			model.addAttribute("message", "로그인 세션이 존재하지 않습니다.");
			return "common/error";
		}
	}
	
	//회원가입
	@RequestMapping(value="enroll.do", method=RequestMethod.POST)
	public String memberInsert(Member member, Model model) {
		logger.info("enroll : " + member);
		
		if(memberService.insertMember(member) > 0) {
			return "common/main";
		}else {
			model.addAttribute("message", "새 회원 등록 실패!");
			return "common/error";
		}
	}
	
	//마이페이지
	@RequestMapping("myinfo.do")
	public ModelAndView myInfoMehod(@RequestParam("userid") String userid, ModelAndView mv) {
		//String userid = request.getParameter("userid");
		
		Member member = memberService.selectMember(userid);
		
		if(member != null) {
			mv.setViewName("member/myInfoPage");
			mv.addObject("member", member);
		}else {
			mv.addObject("message", userid + "에 대한 정보 조회 실패");
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	//수정페이지로 이동
	@RequestMapping("mupview.do")
	public ModelAndView moveUpdateView(@RequestParam("userid") String userid, ModelAndView mv) {
		Member member = memberService.selectMember(userid);
		if(member != null) {
			mv.addObject("member", member);
			mv.setViewName("member/memberUpdatePage");
		}else {
			mv.addObject("message", userid + "에 대한 수정페이지 이동 실패!");
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	//회원 탈퇴
	@RequestMapping("mdel.do")
	public String memberDeleteMethod(@RequestParam("userid") String userid, Model model) {
		if(memberService.deleteMember(userid) > 0) {
			return "redirect:/logout.do";
		}else {
			model.addAttribute("message", userid + "회원 정보 삭제 실패!");
			return "common/error";
		}
	}
	
	//수정페이지 수정하기
	@RequestMapping(value="mupdate.do", method=RequestMethod.POST)
	public String memberUpdateMethod(Member member, Model model) {
		if(memberService.updateMember(member) > 0) {
			return "redirect:/myinfo.do?userid=" + member.getUserid();
		}else {
			model.addAttribute("message", member.getUserid() + " 회원정보 수정 실패!");
			return "common/error";
		}
	}
	
}
