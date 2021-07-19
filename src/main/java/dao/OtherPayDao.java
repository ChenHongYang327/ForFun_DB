package dao;

import java.util.List;

import member.bean.OtherPay;

public interface OtherPayDao {
	
	OtherPay selectById(int otherpayId);
	
	public boolean changeOtherpayStatus (int otherpayID, int status);
	
	public int insert (OtherPay otherPay);
	
	List<OtherPay> selectByTenantId(int tenantId, int orderStaus);
	
	List<OtherPay> selectByOwnerId(int OwnerId, int orderStaus);
}
