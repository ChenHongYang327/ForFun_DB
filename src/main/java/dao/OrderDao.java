package dao;


public interface OrderDao {
	int selectTenantByID(int OrderId);
	
	int selectPublishByID(int orderId);
	
	boolean changeOrderStatus (int orderID, int status);
}
