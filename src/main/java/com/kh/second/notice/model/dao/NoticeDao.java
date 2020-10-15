package com.kh.second.notice.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.second.notice.model.vo.Notice;

@Repository("noticeDao")
public class NoticeDao {
	@Autowired
	private SqlSessionTemplate session;
	
	public NoticeDao() {}
	
	public ArrayList<Notice> selectList() {
	      List<Notice> list = session.selectList("noticeMapper.selectList");
	      return (ArrayList<Notice>)list;	
	}
	
	public Notice selectOne(int noticeNo) {
		return session.selectOne("noticeMapper.selectNotice", noticeNo);
	}
	
	public int insertNotice(Notice notice) {
		return session.insert("noticeMapper.insertNotice", notice);
	}
	
	public int updateNotice(Notice notice) {
		return session.update("noticeMapper.updateNotice", notice);
	}
	
	public int deleteNotice(int noticeNo) {
		return session.delete("noticeMapper.deleteNotice", noticeNo);
	}

	public ArrayList<Notice> selectNewTop3() {
		List<Notice> list = session.selectList("noticeMapper.selectNewTop3");
		return (ArrayList<Notice>)list;
	}
}
