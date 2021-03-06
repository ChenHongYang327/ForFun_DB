package service;

import java.util.List;

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

	// 修改狀態
	public boolean changeOtherpayStatus(int otherpayID, int status) {
		return dao.changeOtherpayStatus(otherpayID, status);
	}

	// 新增一筆資料
	public int insert(OtherPay otherPay) {
		return dao.insert(otherPay);
	}

	// 房客拿otherpay list
	public List<OtherPay> selectByTenntId(int tenantId, int orderStaus, int otherpayStatus) {
		return dao.selectByTenantId(tenantId, orderStaus,otherpayStatus);
	}

	// 房東拿otherpay list
	public List<OtherPay> selectByOwnerId(int ownerId, int orderStaus, int otherpayStatus) {
		return dao.selectByOwnerId(ownerId, orderStaus, otherpayStatus);
	}
	
	public int deleteByAgreementId(int agreementId) {
		return dao.deleteByAgreementId(agreementId);
	}

}
