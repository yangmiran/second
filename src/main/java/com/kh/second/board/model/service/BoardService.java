package com.kh.second.board.model.service;

import java.util.ArrayList;

import com.kh.second.board.model.vo.Board;

public interface BoardService {
	ArrayList<Board> selectAll();
	int insertBoard(Board board);
	int updateBoard(Board board);
	int deleteBoard(int boardno);
	ArrayList<Board> selectTop3();
	ArrayList<Board> selectList(int currentPage, int limit);
	int selectListCount();
	int updateReadCount(int boardno);
	Board selectBoard(int boardno);
	int updateOrigin(Board board);
}
