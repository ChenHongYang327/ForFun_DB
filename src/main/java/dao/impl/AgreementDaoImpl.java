package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.AgreementDao;
import member.bean.Agreement;

public class AgreementDaoImpl implements AgreementDao {
	private DataSource dataSource;

	public AgreementDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public Agreement sellectById(int agreementId) {
		final String sql = " SELECT * FROM FORFUN.agreement WHERE AGREEMENT_ID = ?; ";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			Agreement agreement = new Agreement();

			stmt.setInt(1, agreementId);
			while (rs.next()) {
				agreement.setOrderId(rs.getInt("ORDER_ID"));
				agreement.setStartDate(rs.getTimestamp("START_DATE"));
				agreement.setEndDate(rs.getTimestamp("END_DATE"));
				agreement.setAgreementMoney(rs.getInt("AGREEMENT_MONEY"));
				agreement.setAgreementNote(rs.getString("AGREEMENT_NOTE"));
				agreement.setLandlordSign(rs.getString("LANDLORD_SIGN"));
				agreement.setTenantSign(rs.getString("TENANT＿SIGN"));
				agreement.setCreateTime(rs.getTimestamp("CREATE_TIME"));
			}
			return agreement;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
