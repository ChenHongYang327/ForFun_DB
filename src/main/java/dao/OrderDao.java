package dao;


public interface OrderDao {
	int selectTenantByID(int OrderId);
	
	int selectPublishByID(int orderId);
}
