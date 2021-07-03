package service;

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

}
