package com.kh.second.notice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.second.HomeController;
import com.kh.second.notice.model.service.NoticeService;
import com.kh.second.notice.model.vo.Notice;

@Controller
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping(value="ntop3.do", method=RequestMethod.POST)
	@ResponseBody
	public String noticeTop3(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("ntop3.do run....");
	      
		ArrayList<Notice> list = noticeService.selectNewTop3();
		
		JSONObject sendJSON = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(Notice notice : list) {
			JSONObject job = new JSONObject();
			job.put("no", notice.getNoticeno());
			job.put("title", URLEncoder.encode(notice.getNoticetitle(),"utf-8"));
			job.put("date", notice.getNoticedate().toString());
			
			jarr.add(job);
		} 
		
		
		sendJSON.put("list", jarr);
		
		return sendJSON.toJSONString();
	}
	
	@RequestMapping("nlist.ad")
	public String moveTestAjaxPage() {
		return "notice/noticeListView";
	}
}
