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
import member.bean.Customer_bean;

public class CustomerServiceDaoImpl implements CustomerServiceDao {
	DataSource dataSource;

	public CustomerServiceDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(Customer_bean customer_Service) {
		final String sql = "insert into customer_service(NICK_NAME, MAIL, PHONE, MSG) values(?, ?, ?, ?);";
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

    @Override
    public int deleteById(int customerServiceId) {
        final String sql = "UPDATE customer_service SET DELETE_TIME = ? WHERE CUSTOMER_ID = ?;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, customerServiceId);
            
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0;
    }
	
	@Override
    public Customer_bean selectById(int customerServiceId) {
        final String sql = "SELECT * FROM customer_service WHERE CUSTOMER_ID = ? AND DELETE_TIME IS NULL;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, customerServiceId);
            
            try (ResultSet rs = stmt.executeQuery();) 
            {
                if (rs.next()) {
                    Customer_bean customer = new Customer_bean();
                    customer.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    customer.setType(rs.getInt("TYPE"));
                    customer.setMemberId(rs.getInt("MEMBER_ID"));
                    customer.setNickName(rs.getString("NICK_NAME"));
                    customer.setMail(rs.getString("MAIL"));
                    customer.setPhone(rs.getString("PHONE"));
                    customer.setMag(rs.getString("MSG"));
                    customer.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                    customer.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
                    
                    return customer;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
	
    @Override
    public List<Customer_bean> selectAll() {
        final String sql = "SELECT * FROM customer_service WHERE DELETE_TIME IS NULL;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            List<Customer_bean> customerList = new ArrayList<>();

            while (rs.next()) {
                Customer_bean customer = new Customer_bean();
                customer.setCustomerId(rs.getInt("CUSTOMER_ID"));
                customer.setType(rs.getInt("TYPE"));
                customer.setMemberId(rs.getInt("MEMBER_ID"));
                customer.setNickName(rs.getString("NICK_NAME"));
                customer.setMail(rs.getString("MAIL"));
                customer.setPhone(rs.getString("PHONE"));
                customer.setMag(rs.getString("MSG"));
                customer.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                customer.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
                
                customerList.add(customer);
            }
            
            return customerList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
