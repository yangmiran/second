package com.kh.second.board.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.second.board.model.dao.BoardDao;
import com.kh.second.board.model.vo.Board;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public ArrayList<Board> selectAll() {
		return boardDao.selectList();
	}
	
	@Override
	public ArrayList<Board> selectList(int currentPage, int limit) {
		return boardDao.selectList(currentPage, limit);
	}


	@Override
	public int insertBoard(Board board) {
		return boardDao.insertOriginBoard(board);
	}

	@Override
	public int updateBoard(Board board) {
		return boardDao.updateOrigin(board);
	}

	@Override
	public int deleteBoard(int boardno) {
		return boardDao.deleteBoard(boardno);
	}

	@Override
	public ArrayList<Board> selectTop3() {
		return boardDao.selectTop3();
	}

	@Override
	public int selectListCount() {
		return boardDao.selectListCount();
	}

	@Override
	public int updateReadCount(int boardno) {
		return boardDao.updateReadCount(boardno);
	}

	@Override
	public Board selectBoard(int boardno) {
		return boardDao.selectBoard(boardno);
	}

	@Override
	public int updateOrigin(Board board) {
		return boardDao.updateOrigin(board);
	}


}
