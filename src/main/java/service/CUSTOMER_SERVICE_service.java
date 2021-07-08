package service;


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
	
	
	
	

}
