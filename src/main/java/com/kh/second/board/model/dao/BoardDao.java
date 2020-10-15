package com.kh.second.board.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.second.board.model.vo.Board;
import com.kh.second.board.model.vo.BoardPage;

@Repository("boardDao")
public class BoardDao {
	@Autowired
	private SqlSessionTemplate session;
	
	public BoardDao() {}
	
	public ArrayList<Board> selectList() {
	      List<Board> list = session.selectList("boardMapper.selectList");
	      return (ArrayList<Board>)list;	
	}
	

	public int insertOriginBoard(Board board) {
		return session.insert("boardMapper.insertOriginBoard", board);
	}
	

	public int deleteBoard(int boardNo) {
		return session.delete("boardMapper.deleteBoard", boardNo);
	}

	public ArrayList<Board> selectTop3() {
		List<Board> list = session.selectList("boardMapper.selectTop3");
		return (ArrayList<Board>)list;
	}

	public ArrayList<Board> selectList(int currentPage, int limit) {
		// 전달된 값을 이용해서 출력할 시작행과 끝행을 계산
		int startRow = currentPage;
		int endRow = limit;
		// 2개의 값을 한 객체에 저장
		BoardPage page = new BoardPage(startRow, endRow);
		List<Board> list =  session.selectList("boardMapper.selectList", page);
		return (ArrayList<Board>)list;
	}

	public int selectListCount() {
		return session.selectOne("boardMapper.getListCount");
	}

	public Board selectBoard(int boardNum) {
		return session.selectOne("boardMapper.selectBoard", boardNum);
	}

	public int updateReadCount(int boardNum) {
		return session.update("boardMapper.addReadCount", boardNum);
	}


	public int updateOrigin(Board board) {
		return session.update("boardMapper.updateOrigin", board);
	}
	
	
}
