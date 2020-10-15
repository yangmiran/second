package com.kh.second.test.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.second.test.model.service.TestService;
import com.kh.second.test.model.vo.CryptoUser;
import com.kh.second.test.model.vo.Sample;
import com.kh.second.test.model.vo.User;

@Controller
public class TestController {
	//로그 출력용 객체를 의존성 주입함
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	//패스워드 암호화 처리 메소드 -------------------------------------
	@RequestMapping(value="bcrypto.do", method=RequestMethod.POST)
	public String testBCryptMethod(@RequestParam("userpwd") String userpwd) {
		logger.info("전송온 암호 : " + userpwd);
		
		//암호화 처리
		String bcryptPwd = bcryptPasswordEncoder.encode(userpwd);
		logger.info("암호화된 패스워드 : " + bcryptPwd + ", 글자수 : " + bcryptPwd.length());
		
		return "common/main";
	}
	
	//패스워드 암호화 회원가입 메소드
	@RequestMapping(value="enrollBcrypto.do", method=RequestMethod.POST)
	public String enrollCryptMethod(CryptoUser user, Model model) {
		logger.info("user : " + user);
		
		//패스워드 암호화 처리
		user.setUserpwd(bcryptPasswordEncoder.encode(user.getUserpwd()));
		//서비스로 전송하고 결과받기
		int result = testService.insertUser(user);
		
		if(result > 0) {
			return "common/main";
		}else {
			model.addAttribute("message", "암호화 회원가입 실패!");
			return "common/error";
		}
	}
	
	//암호화된 패스워드 로그인 처리 메소드
	@RequestMapping(value="loginBcrypto.do", method=RequestMethod.POST)
	public String testBCryptLogin(CryptoUser user, Model model) {
		logger.info("암호화 로그인 테스트");
		
		CryptoUser loginUser = testService.selectLogin(user);
		
		//전송온 패스워드(일반글자)와 db 에서 조회해 온 암호화된 패스워드 비교
		//matches() 사용함
		String viewFileName = null;
		if(loginUser != null) {
			if(bcryptPasswordEncoder.matches(user.getUserpwd(), loginUser.getUserpwd())) {
				viewFileName = "common/main";
			}else {
				model.addAttribute("message", "암호가 일치하지 않습니다.");
				viewFileName = "common/error";
			}
		}else {
			model.addAttribute("message", "회원 정보가 존재하지 않습니다.");
			viewFileName = "common/error";
		}
		
		return viewFileName;
	}
	
	//뷰페이지로 이동 처리용 메소드 ------------------------------------
	@RequestMapping("moveAjax.do")
	public String moveTestAjaxPage() {
		return "test/testAjaxPage";
	}
	
	@RequestMapping("moveFile.do")
	public String moveFileUpDownPage() {
		return "test/testFileUpDown";
	}
	
	@RequestMapping("moveCrypto.do")
	public String moveCryptoPage() {
		return "test/testCrypto";
	}
	
	@RequestMapping("moveAOP.do")
	public String moveAOPPage() {
		return "test/testAOPPage";
	}
	
	//파일 업로드 테스트 메소드 ---------------------------------------
	@RequestMapping(value="tinsert.do", method=RequestMethod.POST)
	public String testFileUpload(Sample sample, HttpServletRequest request,
			@RequestParam(name="upfile", required=false) MultipartFile file) {
		logger.info("sample : " + sample);
		logger.info("fife : " + file.getOriginalFilename());
		
		//파일 저장 경로 지정하기
		String savePath = request.getSession().getServletContext().getRealPath("resources/test_files");
		
		//업로드된 파일을 지정한 폴더로 옮기기
		try {
			file.transferTo(new File(savePath + "\\" + file.getOriginalFilename()));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return "common/error";
		}
		
		
		return "common/main";
	}
	
	//파일 다운로드 처리용 메소드
	@RequestMapping("fdown.do")
	public ModelAndView fileDownloadMethod(HttpServletRequest request,
			@RequestParam("fname") String fileName) {
		logger.info("fdown.do : " + fileName);
		
		String savePath = request.getSession().getServletContext().getRealPath("resources/test_files");
		
		File downFile = new File(savePath + "\\" + fileName);
		
		/* ModelAndView(String viewName, String modelName, Object modelObject)
		 * model == request + response
		 * modelName == 이름, modelObject == 객체
		 * request.setAttribute("이름", 객체) 와 같은 의미임
		 * */
		
		//스프링에서는 파일다운하려면, View 를 상속받은 파일다운처리용 뷰클래스를 별도로 작성하고, 
		//DispatcherSErvlet 에 뷰클래스를 실행시키는 뷰리졸버를 등록해야 함.
		//filedownloadview.java파일에 @Component("filedown") 지정되는 이름
		return new ModelAndView("filedown", "downFile", downFile);
	}
	
	
	//ajax test method ---------------------------------------
	@RequestMapping(value="test1.do", method=RequestMethod.POST)
	public void testAjaxMethod1(Sample sample, HttpServletResponse response) 
		throws IOException {
		logger.info("terst1.do run...");
		logger.info("sample : " + sample);
		
		//서비스로 보내고 결과받기 : 생략
		
		//요청한 결과를 클라이언트에게 전송함
		//1. 보내는 정보에 대한 Mime type 지정함
		response.setContentType("text/html; charset=utf-8");
		//2. 출력에 사용할 스트림 생성
		PrintWriter out = response.getWriter();
		
		if(sample.getName().equals("신사임당")) {
			out.append("ok");
			out.flush();
		}else {
			out.append("fail");
			out.flush();
		}
		
		out.close();
	}
	
	//클라이언트에게서 요청을 받으면, json 객체를 리턴하는 메소드
	@RequestMapping(value="test2.do", method=RequestMethod.POST)
	@ResponseBody //리턴하는 json 문자열을 response 객체에 담아서 보내라는 의미의 어노테이션임
	public String testAjaxMethod2(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("test2.do run...");
		response.setContentType("application/json; charset=utf-8");
		
		JSONObject job = new JSONObject();
		job.put("no", 123);
		job.put("title", "test return json object");
		job.put("writer", URLEncoder.encode("홍길동", "utf-8"));
		job.put("content", URLEncoder.encode("json 객체를 뷰리졸버를 통해 리턴하는 테스트 글임", "utf-8"));
		
		return job.toJSONString(); //뷰리졸버로 리턴함
	}
	
	@RequestMapping(value="test3.do", method=RequestMethod.POST)
	@ResponseBody
	public String testAjaxMethod3(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("test3.do run....");
	      
	      //List를 json 배열로 바꾸고, 요청한 페이지로 전송함.
	      List<User> list = new ArrayList<User>();
	      
	      list.add(new User("u111", "p111", "홍길동", 25, "hong111@kh.org", 
	    		  "010-1111-1111", new java.sql.Date(new java.util.GregorianCalendar(1993, 10, 24).getTimeInMillis())));
	      list.add(new User("u222", "p222", "홍길동", 28, "hong222@kh.org", 
	    		  "010-2222-2222", new java.sql.Date(new java.util.GregorianCalendar(1993, 10, 24).getTimeInMillis())));
	      list.add(new User("u333", "p333", "홍길동", 48, "hong333@kh.org", 
	    		  "010-3333-3333", new java.sql.Date(new java.util.GregorianCalendar(1993, 10, 24).getTimeInMillis())));
	      list.add(new User("u444", "p444", "이순신", 39, "hong444@kh.org", 
	    		  "010-4444-4444", new java.sql.Date(new java.util.GregorianCalendar(1993, 8, 10).getTimeInMillis())));
	      list.add(new User("u555", "p555", "김유신", 25, "hong555@kh.org", 
	    		  "010-5555-5555", new java.sql.Date(new java.util.GregorianCalendar(1995, 9, 26).getTimeInMillis())));
	      
	      //전송용 json 객체 준비
	      JSONObject sendJson = new JSONObject();
	      //json 배열 객체 생성
	      JSONArray jarr = new JSONArray();
	      
	      //list 를 jarr 로 옮기기 (복사)
	      for(User user : list) {
	    	  //user 객체 저장용 json 객체 생성
	    	  JSONObject job = new JSONObject();
	    	  
	    	  job.put("userid", user.getUserid());
	    	  job.put("userpwd", user.getUserpwd());
	    	  job.put("username", URLEncoder.encode(user.getUsername(), "utf-8"));
	    	  job.put("age", user.getAge());
	    	  job.put("email", user.getEmail());
	    	  job.put("phone", user.getPhone());
	    	  job.put("birth", user.getBirth().toString()); //날짜는 반드시  String으로 변환
	    	  
	    	  //jarr에 json 객체 저장
	    	  jarr.add(job);
	      }
	      
	      sendJson.put("list", jarr);
	      
	      return sendJson.toJSONString(); //jsonView로 리턴됨.
	}
	
	@RequestMapping(value="test4.do", method=RequestMethod.POST)
	public ModelAndView testAjaxMehod4(ModelAndView mv) throws UnsupportedEncodingException {
		logger.info("test4.do run...");
		
		//Map 객체를 ModelAndView 객체에 담아서 JsonView 로 보냄
		//그러면 json 객체로 클라이언트에게 전송감
		Sample samp = new Sample("이 율곡", 55);
		samp.setName(URLEncoder.encode(samp.getName(), "utf-8"));
		
		Map<String, Sample> map = new HashMap<String, Sample>();
		map.put("samp", samp);
		
		mv.addObject(map);
		//servlet-context.xml 에 bean 등록된 JsonView 의 id 명을
		//뷰 이름으로 지정함
		mv.setViewName("jsonView");
		
		return mv; //map 객체가 json 객체로 변경되어 전송됨
	}
	
	@RequestMapping(value="test5.do", method=RequestMethod.POST)
	public ResponseEntity<String> testAjaxMehod5(
			@RequestBody String param) throws ParseException{
		//post 방식으로 전송온 request body 에 기록된 json 문자열을 꺼내서 param 변수에 기록 저장함
		logger.info("test5.do run...");
		
		//받은 json 문자열을 json 객체로 바꿈
		JSONParser jparser = new JSONParser();
		JSONObject job = (JSONObject)jparser.parse(param);
		
		//json 객체가 가진 필드값 추출해서, Sample 객체로 복사하기
		Sample sample = new Sample();
		sample.setName((String)job.get("name"));
		sample.setAge(((Long)job.get("age")).intValue());
		logger.info(sample.toString());
		
		//클라이언트에게 응답하는 용도의 객체 : ResponseEntity<T>
		//뷰리졸버가 아닌 출력스트림으로 나감
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@RequestMapping(value="test6.do", method=RequestMethod.POST)
	public ResponseEntity<String> testAjaxMethod6(
			@RequestBody String param) throws ParseException{
		//post 전송이므로 request body 에 기록되어 요청온 json 배열 문자열을 param에 기록 저장함
		logger.info("test6.do run...");
		
		//전송온 문자열을 json array 객체로 변환
		JSONParser jparser = new JSONParser();
		JSONArray jarr = (JSONArray)jparser.parse(param);
		
		//jarr 의 정보를 ArrayList<Sample> 옮겨 저장하고 출력 확인
		ArrayList<Sample> list = new ArrayList<Sample>();
		
		for(int i = 0; i < jarr.size(); i++) {
			JSONObject job = (JSONObject)jarr.get(i);
			
			Sample sample = new Sample((String)job.get("name"), ((Long)job.get("age")).intValue());
			list.add(sample);
		}
		
		logger.info("list : " + list);
		
		return new ResponseEntity<String>("seccess", HttpStatus.OK);
	}
}
