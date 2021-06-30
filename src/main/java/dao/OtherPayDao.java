package dao;

import member.bean.OtherPay;

public interface OtherPayDao {
	
	OtherPay selectById(int otherpayId);
	
	public boolean changeOtherpayStatus (int otherpayID, int status);
	
}
