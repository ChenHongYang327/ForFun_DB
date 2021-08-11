package service;


import java.util.List;

import controller.Report_page_Servlet;
import dao.Report_pageDao;
import dao.impl.Report_pageDaoImpl;
import member.bean.Report_page_bean;

public class Report_page_service {
	Report_pageDao report_pageDao;

	public Report_page_service() {
		report_pageDao = new Report_pageDaoImpl();
	}
	
	public int insertPost(Report_page_bean Report_page) {
		return report_pageDao.insertPost(Report_page);
	}
	
	public int insertUser(Report_page_bean Report_page) {
		return report_pageDao.insertUser(Report_page);
	}
	
	public int insertCharoom(Report_page_bean Report_page) {
		return report_pageDao.insertCharoom(Report_page);
	}
	
	//IOS 搜尋全部留言的檢舉物件
	public List<Report_page_bean> selectAllChatMsg(){
		return report_pageDao.selectAllChatMsg();
	}
	
	public List<Report_page_bean> selectReportMember(){
		return report_pageDao.selectReportMember();
	}
	
	public int deleteById(int reportId) {
		return report_pageDao.deleteById(reportId);
	}
	
	public List<Report_page_bean> selectReportPost() {
		return report_pageDao.selectReportPost();
	}
	
	public int deleteBypostId(int postId) {
		return report_pageDao.deleteBypostId(postId);
	}
	
	public int updateType(int REPORT_ID) {
		return report_pageDao.updateType(REPORT_ID);
	}
}
