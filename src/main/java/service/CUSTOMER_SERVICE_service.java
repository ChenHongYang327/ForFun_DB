package service;


import java.util.List;

import dao.CustomerServiceDao;
import dao.impl.CustomerServiceDaoImpl;
import member.bean.Customer_bean;

public class CUSTOMER_SERVICE_service {
	CustomerServiceDao customerServiceDao;

	public CUSTOMER_SERVICE_service() {
		customerServiceDao= new CustomerServiceDaoImpl();
	}
	
	public int insert(Customer_bean customer_Service) {
		return customerServiceDao.insert(customer_Service);
	}
	
	
	public int deleteById(int customerServiceId) {
	    return customerServiceDao.deleteById(customerServiceId);
	}
    
	public Customer_bean selectById(int customerServiceId) {
	    return customerServiceDao.selectById(customerServiceId);
	}
    
	public List<Customer_bean> selectAll() {
	    return customerServiceDao.selectAll();
	}
}
