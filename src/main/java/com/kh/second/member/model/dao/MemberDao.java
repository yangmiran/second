package com.kh.second.member.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.second.member.model.vo.Member;
import com.kh.second.member.model.vo.SearchDate;

@Repository("memberDao")
public class MemberDao {
	//스프링-마이바티스 연동 객체 사용 : root_context.xml 에 선언되어 있음
	@Autowired
	private SqlSessionTemplate session;
	
	public MemberDao() {}
	
	public Member selectLogin(Member member) {
		return session.selectOne("memberMapper.loginCheck", member);
	}

	public int insertMember(Member member) {
		return session.insert("memberMapper.insertMember", member);
	}

	public Member selectMember(String userid) {
		return session.selectOne("memberMapper.selectMember", userid);
	}

	public int deleteMember(String userid) {
		return session.delete("memberMapper.deleteMember", userid);
	}
	
	public int updateMember(Member member) {
		return session.update("memberMapper.updateMember", member);
	}

	public ArrayList<Member> selectList() {
		List<Member> list = session.selectList("memberMapper.selectList");
		return (ArrayList<Member>)list;
	}

	public int updateLoginOK(Member member) {
		return session.update("memberMapper.updateLoginOK", member);
	}

	public ArrayList<Member> selectSearchUserid(String keyword) {
		List<Member> list = session.selectList("memberMapper.selectSearchUserid", keyword);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchGender(String keyword) {
		List<Member> list = session.selectList("memberMapper.selectSearchGender", keyword);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchAge(int keyword) {
		List<Member> list = session.selectList("memberMapper.selectSearchAge", keyword);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchEnrollDate(SearchDate date) {
		List<Member> list = session.selectList("memberMapper.selectSearchEnrollDate", date);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchLoginOk(String keyword) {
		List<Member> list = session.selectList("memberMapper.selectSearchLoginOk", keyword);
		return (ArrayList<Member>)list;
	}

	public int selectCheckId(String userid) {
		return session.selectOne("memberMapper.selectCheckId", userid);
	}


}
