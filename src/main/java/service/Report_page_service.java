package service;


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
	
	
	

	
	
	

}