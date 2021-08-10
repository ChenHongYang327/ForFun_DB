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


	@Override
	public List<Report_page_bean> selectAllChatMsg() {
		final String sql = "SELECT * FROM FORFUN.report WHERE ITEM = 1 AND TYPE = 0;";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			List<Report_page_bean> reports = new ArrayList<>();
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					
					Report_page_bean report = new Report_page_bean();
					report.setReport_id(rs.getInt("REPORT_ID"));
					report.setWhistleblower_id(rs.getInt("WHISTLEBLOWER_ID"));
					report.setReported_id(rs.getInt("REPORTED_ID"));
					report.setType(rs.getInt("TYPE"));
					report.setMessage(rs.getString("MESSAGE"));
					report.setReport_class(rs.getInt("REPORT_CLASS"));
					report.setChatroom_id(rs.getInt("CHATROOM_ID"));
					report.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					
					reports.add(report);
					
				}
				return reports;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Report_page_bean> selectReportMember() {
		final String sql="SELECT * FROM FORFUN.report WHERE ITEM=2 and DELETE_TIME is null";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			List<Report_page_bean> reports=new ArrayList<Report_page_bean>();
			ResultSet rs= pstmt.executeQuery(sql);
			while(rs.next()) {
				Report_page_bean report=new Report_page_bean();
				report.setReport_id(rs.getInt("REPORT_ID"));
				report.setWhistleblower_id(rs.getInt("WHISTLEBLOWER_ID"));
				report.setReported_id(rs.getInt("REPORTED_ID"));
				report.setType(rs.getInt("TYPE"));
				report.setMessage(rs.getString("MESSAGE"));
				report.setReport_class(rs.getInt("REPORT_CLASS"));
				report.setPost_id(rs.getInt("POST_ID"));
				report.setChatroom_id(rs.getInt("CHATROOM_ID"));
				report.setItem(rs.getInt("ITEM"));
				report.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				report.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
				reports.add(report);
			}
			return reports;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public int deleteById(int reportId) {
		  final String sql = "UPDATE FORFUN.report SET DELETE_TIME = ? WHERE REPORT_ID = ?;";
	        
	        try (
	            Connection conn = dataSource.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql);
	        ) {
	            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
	            stmt.setInt(2, reportId);
	            
	            return stmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return -1;
	}

	
	@Override
		public List<Report_page_bean> selectReportPost() {
			final String sql="SELECT * FROM FORFUN.report WHERE ITEM = 0 and DELETE_TIME is null;";
			try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
				List<Report_page_bean> reports=new ArrayList<Report_page_bean>();
				ResultSet rs= pstmt.executeQuery(sql);
				while(rs.next()) {
					Report_page_bean report=new Report_page_bean();
					report.setReport_id(rs.getInt("REPORT_ID"));
					report.setWhistleblower_id(rs.getInt("WHISTLEBLOWER_ID"));
					report.setReported_id(rs.getInt("REPORTED_ID"));
					report.setType(rs.getInt("TYPE"));
					report.setMessage(rs.getString("MESSAGE"));
					report.setReport_class(rs.getInt("REPORT_CLASS"));
					report.setPost_id(rs.getInt("POST_ID"));
					report.setChatroom_id(rs.getInt("CHATROOM_ID"));
					report.setItem(rs.getInt("ITEM"));
					report.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					report.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
					reports.add(report);
				}
				return reports;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}	
		
		
		@Override
		public int deleteBypostId(int postId) {
			  final String sql = "UPDATE FORFUN.report SET DELETE_TIME = ? WHERE POST_ID = ?;";
		        
		        try (
		            Connection conn = dataSource.getConnection();
		            PreparedStatement stmt = conn.prepareStatement(sql);
		        ) {
		            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
		            stmt.setInt(2, postId);
		            
		            return stmt.executeUpdate();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
		        return -1;
		}

}
