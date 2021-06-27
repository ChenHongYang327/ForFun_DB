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

	@Override
	public boolean changeOrderStatus(int orderID, int status) {
	    final String sql = "UPDATE FORFUN.order SET OTHERPAY_STATUS = ? WHERE OTHERPAY_ID = ? ;";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
        	stmt.setInt(1, status);
        	stmt.setInt(2, orderID);
        	
        	return stmt.execute();
      
        }catch (Exception e) {
            e.printStackTrace();
        }

		return false;
	}
	
	@Override
	public Order selectByID(int OrderId) {
		final String sql = "select * from FORFUN.order where ORDER_ID = ?";
		Order order=new Order();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, OrderId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				order.setOrderId(rs.getInt("ORDER_ID"));
				order.setPublishId(rs.getInt("PUBLISH_ID"));
				order.setTenantId(rs.getInt("TENANT_ID"));
				order.setTenantId(rs.getInt("PUBLISH_STAR"));
				order.setPublishComment(rs.getString("PUBLISH_COMMENT"));
				order.setOrderStatus(rs.getInt("ORDER_STATUS"));
				order.setRead(rs.getBoolean("READ"));
				order.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				order.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				order.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}
	
	@Override
	public Order selectByPublishID(int PublishId) {
		final String sql = "select * from FORFUN.order where PUBLISH_ID = ?";
		Order order=new Order();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1,PublishId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				order.setOrderId(rs.getInt("ORDER_ID"));
				order.setPublishId(rs.getInt("PUBLISH_ID"));
				order.setTenantId(rs.getInt("TENANT_ID"));
				order.setTenantId(rs.getInt("PUBLISH_STAR"));
				order.setPublishComment(rs.getString("PUBLISH_COMMENT"));
				order.setOrderStatus(rs.getInt("ORDER_STATUS"));
				order.setRead(rs.getBoolean("READ"));
				order.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				order.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				order.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}


}
