package service;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;

public class OrderService {
	OrderDao orderDao;

	public OrderService() {
		orderDao=new OrderDaoImpl();
	}
	public int selectTenantByID(int OrderId) {
		return orderDao.selectTenantByID(OrderId);
	}
	
	//find publish id
	public int selectPublishByID(int orderId) {
		return orderDao.selectPublishByID(orderId);
	}
	
	
}
