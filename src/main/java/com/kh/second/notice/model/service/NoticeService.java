package com.kh.second.notice.model.service;

import java.util.ArrayList;

import com.kh.second.notice.model.vo.Notice;

public interface NoticeService {

   ArrayList<Notice> selectAll();
   Notice selectOne(int noticeno);
   int insertNotice(Notice notice);
   int updateNotice(Notice notice);
   int deleteNotice(int noticeno);
   ArrayList<Notice> selectNewTop3();
}