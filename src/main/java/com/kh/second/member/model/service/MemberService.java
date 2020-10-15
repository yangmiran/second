package com.kh.second.member.model.service;

import java.util.ArrayList;

import com.kh.second.member.model.vo.Member;

public interface MemberService {
   Member selectLogin(Member member);
   int insertMember(Member member);
   int updateMember(Member member);
   int deleteMember(String userid);
   ArrayList<Member> selectList();
   Member selectMember(String userid);
}