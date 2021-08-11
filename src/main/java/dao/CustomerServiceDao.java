package dao;

import java.util.List;

import member.bean.Customer_bean;

public interface CustomerServiceDao {

	int insert(Customer_bean customerService);
	
	int deleteById(int customerServiceId);
	
	Customer_bean selectById(int customerServiceId);
	
	List<Customer_bean> selectAll();
}
