package dao;

import member.bean.Order;

public interface OrderDao {
	int selectTenantByID(int OrderId);
	
	int selectPublishByID(int orderId);
	
	boolean changeOrderStatus (int orderID, int status);
	
	Order selectByID(int OrderId);
	
	Order selectByPublishID(int PublishId);
}
