package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.CustomerServiceDao;
import dao.MemberDao;
import member.bean.Customer_bean;
import member.bean.Member;

public class CustomerServiceDaoImpl implements CustomerServiceDao {
	DataSource dataSource;

	public CustomerServiceDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(Customer_bean customer_Service) {
		final String sql = "insert into customer_service(NICK_NAME, MAIL, PHONE, MESSAGE) values(?, ?, ?, ?);";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, customer_Service.getNickName());
			pstmt.setString(2, customer_Service.getMail());
			pstmt.setString(3, customer_Service.getPhone());
			pstmt.setString(4, customer_Service.getMag());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
