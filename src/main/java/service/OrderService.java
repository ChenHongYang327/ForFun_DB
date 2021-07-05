package service;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import member.bean.Order;

public class OrderService {
	OrderDao orderDao;

	public OrderService() {
		orderDao = new OrderDaoImpl();
	}

	public int insert(Order order) {
	    return orderDao.insert(order);
	}
	
	// 透過 訂單ID 查詢 房客ID
	// 假資料待改
	public int selectTenantByID(int OrderId) {
		return orderDao.selectTenantByID(OrderId);
	}

	// 透過 訂單ID 查詢 刊登單ID
	public int selectPublishByID(int orderId) {
		return orderDao.selectPublishByID(orderId);
	}

	// 修改狀態
	public boolean changeOrderStatus(int orderID, int status) {
		return orderDao.changeOrderStatus(orderID, status);
	}

	public Order selectByID(int OrderId) {
		return orderDao.selectByID(OrderId);
	}

	// 透過 刊登單ID 查詢 訂單ID
	public Order selectByPublishID(int PublishId) {
		return orderDao.selectByPublishID(PublishId);
	}
	
	// 透過 訂單ID 存房子物件評價＆星數
	public int insertEvaluation(Order evaluation, int orderId) {
		return orderDao.insertEvaluation(evaluation, orderId);
	}

}
