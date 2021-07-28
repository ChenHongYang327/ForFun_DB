package dao;

import java.util.List;

import member.bean.Agreement;

public interface AgreementDao {
	
	Agreement sellectById (int agreementId);
	
	int insertHouseOwner(Agreement agreement);
	
	int updateTenant (String tenantSignPath, int agreementId);
	
	int selectAgmtidByOrderid(int orderId) ;
	
	int selecOrderidByAgreementid(int agreementId);
	
	int deleteByOrderId(int orderId);
	
	Agreement selectByOrderId(int orderId);

}
