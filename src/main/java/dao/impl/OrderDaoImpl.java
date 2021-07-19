package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.OrderDao;
import member.bean.Order;

public class OrderDaoImpl implements OrderDao {
	DataSource dataSource;

	public OrderDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

    @Override
    public int insert(Order order) {
        final String sql = "INSERT INTO FORFUN.order (PUBLISH_ID, TENANT_ID, ORDER_STATUS, `READ`, CREATE_TIME) values (?, ?, ?, ?, ?) ; ";

        try (Connection conn = dataSource.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, order.getPublishId());
            stmt.setInt(2, order.getTenantId());
            stmt.setInt(3, order.getOrderStatus());
            stmt.setBoolean(4, order.getRead());
            stmt.setTimestamp(5, order.getCreateTime());
            
            return stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
	
	// 取得訂單的房客
	@Override
	public int selectTenantByID(int OrderId) {
		final String sql = "select TENANT_ID from FORFUN.order where ORDER_ID = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, OrderId);
			ResultSet rs = pstmt.executeQuery();
			int tenantID = -1;
			while (rs.next()) {
				tenantID = rs.getInt(1);
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
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();
			int publishID = -1;
			while (rs.next()) {
				publishID = rs.getInt("PUBLISH_ID");
			}
			return publishID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public boolean changeOrderStatus(int orderID, int status) {
		final String sql = "UPDATE FORFUN.order SET ORDER_STATUS = ? WHERE ORDER_ID = ? ;";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, status);
			stmt.setInt(2, orderID);

			return stmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Order selectByID(int OrderId) {
		final String sql = "select * from FORFUN.order where ORDER_ID = ?";
		Order order = new Order();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, OrderId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				order.setOrderId(rs.getInt("ORDER_ID"));
				order.setPublishId(rs.getInt("PUBLISH_ID"));
				order.setTenantId(rs.getInt("TENANT_ID"));
				order.setPublishStar(rs.getInt("PUBLISH_STAR"));
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
		Order order = new Order();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, PublishId);
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
	public int insertEvaluation(Order evaluation, int orderId) {
		final String sql = "UPDATE FORFUN.order SET PUBLISH_STAR = ?, PUBLISH_COMMENT = ? WHERE ORDER_ID = ? ; ";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, evaluation.getPublishStar());
			stmt.setString(2, evaluation.getPublishComment());
			stmt.setInt(3, orderId);

			return stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public List<Order> selectAllBySatus(int orderStatus, int tenantId) {
		final String sql = "SELECT * FROM FORFUN.order WHERE ORDER_STATUS = ? AND TENANT_ID = ? ;";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, orderStatus);
			stmt.setInt(2, tenantId);

			List<Order> orders = new ArrayList<Order>();
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getInt("ORDER_ID"));
					order.setPublishId(rs.getInt("PUBLISH_ID"));
					order.setTenantId(rs.getInt("TENANT_ID"));
					order.setPublishStar(rs.getInt("PUBLISH_STAR"));
					order.setPublishComment(rs.getString("PUBLISH_COMMENT"));
					order.setOrderStatus(rs.getInt("ORDER_STATUS"));
					order.setRead(rs.getBoolean("READ"));
					order.setCreateTime(rs.getTimestamp("CREATE_TIME"));

					orders.add(order);
				}
				return orders;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
    public List<Order> selectAllByPublishID(int publishId) {
        final String sql = "select * from FORFUN.order where PUBLISH_ID = ? AND PUBLISH_STAR is not null";
        
        List<Order> orderList = new ArrayList<Order>();
        
        try (
            Connection conn = dataSource.getConnection(); 
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, publishId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("ORDER_ID"));
                order.setPublishId(rs.getInt("PUBLISH_ID"));
                order.setTenantId(rs.getInt("TENANT_ID"));
                order.setPublishStar(rs.getInt("PUBLISH_STAR"));
                order.setPublishComment(rs.getString("PUBLISH_COMMENT"));
                order.setOrderStatus(rs.getInt("ORDER_STATUS"));
                order.setRead(rs.getBoolean("READ"));
                order.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                order.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
                order.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
                
                orderList.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return orderList;
    }

	@Override
	public List<Order> selectAllByOwnerandSatus(int orderStatus, int ownerId) {
		final String sql = "select o.ORDER_ID,o.PUBLISH_ID,o.TENANT_ID,o.PUBLISH_STAR,o.PUBLISH_COMMENT,o.ORDER_STATUS,o.READ,o.CREATE_TIME,o.UPDATE_TIME,o.DELETE_TIME,p.OWNER_ID " + 
				"from FORFUN.order o left join FORFUN.publish p on o.PUBLISH_ID = p.PUBLISH_ID " + 
				"where	o.ORDER_STATUS = ? AND p.OWNER_ID = ?; ";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, orderStatus);
			stmt.setInt(2, ownerId);

			List<Order> orders = new ArrayList<Order>();
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getInt("ORDER_ID"));
					order.setPublishId(rs.getInt("PUBLISH_ID"));
					order.setTenantId(rs.getInt("TENANT_ID"));
					order.setPublishStar(rs.getInt("PUBLISH_STAR"));
					order.setPublishComment(rs.getString("PUBLISH_COMMENT"));
					order.setOrderStatus(rs.getInt("ORDER_STATUS"));
					order.setRead(rs.getBoolean("READ"));
					order.setCreateTime(rs.getTimestamp("CREATE_TIME"));

					orders.add(order);
				}
				return orders;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Order> selectAllEvaluationByPublishID(int publishId) {
	    final String sql = "select * from FORFUN.order where PUBLISH_ID = ? AND PUBLISH_STAR is not null";
        
        List<Order> orderList = new ArrayList<Order>();
        
        try (
            Connection conn = dataSource.getConnection(); 
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, publishId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("ORDER_ID"));
                order.setPublishId(rs.getInt("PUBLISH_ID"));
                order.setTenantId(rs.getInt("TENANT_ID"));
                order.setPublishStar(rs.getInt("PUBLISH_STAR"));
                order.setPublishComment(rs.getString("PUBLISH_COMMENT"));
                order.setOrderStatus(rs.getInt("ORDER_STATUS"));
                order.setRead(rs.getBoolean("READ"));
                order.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                order.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
                order.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
                
                orderList.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return orderList;
	}

    @Override
    public Order selectByPublishIDAndTenantID(int publishId, int tenantID) {
        final String sql = "select * from FORFUN.order where PUBLISH_ID = ? and TENANT_ID = ?";
        
        try (
            Connection conn = dataSource.getConnection(); 
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, publishId);
            pstmt.setInt(2, tenantID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
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
                
                return order;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
