package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.Report_pageDao;
import dao.MemberDao;
import member.bean.Customer_bean;
import member.bean.Member;
import member.bean.Report_page_bean;

public class Report_pageDaoImpl implements Report_pageDao {
	DataSource dataSource;

	public Report_pageDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}


	@Override
	public int insertPost(Report_page_bean report_page_bean) {
		final String sql = "insert into report(WHISTLEBLOWER_ID, REPORTED_ID, MESSAGE, REPORT_CLASS, POST_ID, ITEM) values( ?, ?, ?, ?, ?, ? );";
		
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, report_page_bean.getWhistleblower_id());
			pstmt.setInt(2, report_page_bean.getReported_id());
			pstmt.setString(3, report_page_bean.getMessage());
			pstmt.setInt(4, report_page_bean.getReport_class());
			pstmt.setInt(5, report_page_bean.getPost_id());
			pstmt.setInt(6, report_page_bean.getItem());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int insertUser(Report_page_bean report_page_bean) {
		final String sql = "insert into report(WHISTLEBLOWER_ID, REPORTED_ID, MESSAGE, REPORT_CLASS, ITEM) values(?, ?, ?, ?, ? );";
		

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, report_page_bean.getWhistleblower_id());
			pstmt.setInt(2, report_page_bean.getReported_id());
			pstmt.setString(3, report_page_bean.getMessage());
			pstmt.setInt(4, report_page_bean.getReport_class());
			pstmt.setInt(5, report_page_bean.getItem());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int insertCharoom(Report_page_bean report_page_bean) {
		final String sql = "insert into report(WHISTLEBLOWER_ID, REPORTED_ID, MESSAGE, REPORT_CLASS, CHATROOM_ID) values(?, ?, ?, ?, ? );";
		

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, report_page_bean.getWhistleblower_id());
			pstmt.setInt(2, report_page_bean.getReported_id());
			pstmt.setString(3, report_page_bean.getMessage());
			pstmt.setInt(4, report_page_bean.getReport_class());
			pstmt.setInt(5, report_page_bean.getChatroom_id());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	

	

}
