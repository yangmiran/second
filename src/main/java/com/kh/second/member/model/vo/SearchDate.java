package com.kh.second.member.model.vo;

import java.sql.Date;

public class SearchDate implements java.io.Serializable {
	private static final long SerialVersionUID = 9999L;
	
	private Date begin;
	private Date end;
	
	public SearchDate() {}
	
	public SearchDate(Date begin, Date end) {
		super();
		this.begin = begin;
		this.end = end;
	}
	
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "SearchDate [begin=" + begin + ", end=" + end + "]";
	}

}
