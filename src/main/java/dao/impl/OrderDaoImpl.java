package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.OrderDao;
import member.bean.Order;

public class OrderDaoImpl implements OrderDao {
	DataSource dataSource;

	public OrderDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	//取得訂單的房客
	@Override
	public int selectTenantByID(int OrderId) {
		final String sql = "select TENANT_ID from FORFUN.order where ORDER_ID = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, OrderId);
			ResultSet rs = pstmt.executeQuery();
			int tenantID = -1;
			while (rs.next()) {
				tenantID=rs.getInt(1);
			}
			return tenantID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int selectPublishByID(int orderId) {
		final String sql = "select PUBLISH_ID from FORFUN.order where ORDER_ID = ?";
		try (Connection conn = dataSource.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();
			int publishID = -1;
			while (rs.next()) {
				publishID=rs.getInt(1);
			}
			return publishID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
