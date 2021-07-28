package service;

import java.util.List;

import dao.AgreementDao;
import dao.impl.AgreementDaoImpl;
import member.bean.Agreement;

public class AgreementService {
	private AgreementDao dao;

	public AgreementService() {
		dao = new AgreementDaoImpl();
	}

	public Agreement sellectById(int agreementId) {
		return dao.sellectById(agreementId);
	}
	
	public int insertHouseOwner (Agreement agreement) {
		return dao.insertHouseOwner(agreement);
	}
	
	public int updateTenant (String tenantSignPath, int agreementId) {
		return dao.updateTenant(tenantSignPath, agreementId);
	}
	
	public int selectAgmtidByOrderid(int orderId) {
		return dao.selectAgmtidByOrderid(orderId);
	}
	
	public int selecOrderidByAgreementid(int agreementId) {
		return dao.selecOrderidByAgreementid(agreementId);
	}
	
	public int deleteByOrderId(int orderId) {
		return dao.deleteByOrderId(orderId);
	}
	
	public Agreement selectByOrderId(int orderId) {
		return dao.selectByOrderId(orderId);
	}


}
