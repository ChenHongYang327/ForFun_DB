package dao;

import java.util.List;

import controller.Report_page_Servlet;
import member.bean.Report_page_bean;

public interface Report_pageDao {

	int insertPost(Report_page_bean report_page_bean);
	
	int insertUser(Report_page_bean report_page_bean);
	
	int insertCharoom(Report_page_bean report_page_bean);

	List<Report_page_bean> selectAllChatMsg();

}
