package dao;

import member.bean.Agreement;

public interface AgreementDao {
	
	Agreement sellectById (int agreementId);
	
	int insertHouseOwner(Agreement agreement);
	
	int updateTenant (String tenantSignPath, int agreementId);
	
	int selectAgmtidByOrderid(int orderId) ;
	
	int selecOrderidByAgreementid(int agreementId);

}
