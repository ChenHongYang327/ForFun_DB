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
	
	//修改狀態
	public boolean changeOrderStatus (int orderID, int status) {
		return orderDao.changeOrderStatus(orderID, status);
	}
	
}
