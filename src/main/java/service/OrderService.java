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
}
