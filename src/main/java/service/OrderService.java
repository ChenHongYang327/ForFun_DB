package service;

import java.util.List;

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
	public int selectTenantByID(int orderId) {
		return orderDao.selectTenantByID(orderId);
	}

	// 透過 訂單ID 查詢 刊登單ID
	public int selectPublishByID(int orderId) {
		return orderDao.selectPublishByID(orderId);
	}

	// 修改 訂單狀態
	public boolean changeOrderStatus(int orderID, int status) {
		return orderDao.changeOrderStatus(orderID, status);
	}

	public Order selectByID(int OrderId) {
		return orderDao.selectByID(OrderId);
	}

	// 透過 刊登單ID 查詢 訂單
	public Order selectByPublishID(int publishId) {
		return orderDao.selectByPublishID(publishId);
	}

	// 透過 訂單ID 存房子物件評價＆星數
	public int insertEvaluation(Order evaluation, int orderId) {
		return orderDao.insertEvaluation(evaluation, orderId);
	}

	// 透過 房客ID ＆ order狀態碼 拿order list
	public List<Order> selectAllBySatus(int orderStatus, int tenantId) {
		return orderDao.selectAllBySatus(orderStatus, tenantId);
	}

	public List<Order> selectAllByPublishID(int publishId) {
		return orderDao.selectAllByPublishID(publishId);
	}

	// 透過 房東ID ＆ order狀態碼 拿order list
	public List<Order> selectAllByOwnerandSatus(int orderStatus, int ownerId) {
		return orderDao.selectAllByOwnerandSatus(orderStatus, ownerId);
	}

	// 透過 otherpayID 查詢 訂單
	public Order selectByotherpayID(int otherpayId) {
		return orderDao.selectByotherpayID(otherpayId);
	}
	//只要id符合全刪
		public int deleteById(int orderId) {
			return orderDao.deleteById(orderId);
		}

}
