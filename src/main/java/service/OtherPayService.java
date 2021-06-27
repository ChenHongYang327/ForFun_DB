package service;

import dao.OtherPayDao;
import dao.impl.OtherPayDaoImpl;
import member.bean.OtherPay;

public class OtherPayService {
	private OtherPayDao dao = new OtherPayDaoImpl();
	
	public OtherPayService() {
		dao = new OtherPayDaoImpl();
	}
	
	public OtherPay selectById(int otherpayId) {
		return dao.selectById(otherpayId);
	}
	
	//修改狀態
	public boolean changeOtherpayStatus (int otherpayID, int status) {
		return dao.changeOtherpayStatus(otherpayID,status);
	}
	
}
