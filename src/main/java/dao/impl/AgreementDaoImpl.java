package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.AgreementDao;
import member.bean.Agreement;
import member.bean.Member;

public class AgreementDaoImpl implements AgreementDao {
	private DataSource dataSource;

	public AgreementDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public Agreement sellectById(int agreementId) {
		final String sql = " SELECT * FROM FORFUN.agreement WHERE AGREEMENT_ID = ? AND DELETE_TIME is null; ";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			stmt.setInt(1, agreementId);
			ResultSet rs = stmt.executeQuery();
			Agreement agreement = new Agreement();
			while (rs.next()) {
				agreement.setOrderId(rs.getInt("ORDER_ID"));
				agreement.setStartDate(rs.getTimestamp("START_DATE"));
				agreement.setEndDate(rs.getTimestamp("END_DATE"));
				agreement.setAgreementMoney(rs.getInt("AGREEMENT_MONEY"));
				agreement.setAgreementNote(rs.getString("AGREEMENT_NOTE"));
				agreement.setLandlordSign(rs.getString("LANDLORD_SIGN"));
				agreement.setTenantSign(rs.getString("TENANT_SIGN"));
				agreement.setCreateTime(rs.getTimestamp("CREATE_TIME"));
			}
			return agreement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int insertHouseOwner(Agreement agreement) {
		final String sql = "INSERT INTO FORFUN.agreement (ORDER_ID, START_DATE, END_DATE, AGREEMENT_MONEY, LANDLORD_SIGN) VALUES (?, ?, ?, ?, ?);";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, agreement.getOrderId());
			stmt.setTimestamp(2, agreement.getStartDate());
			stmt.setTimestamp(3, agreement.getEndDate());
			stmt.setInt(4, agreement.getAgreementMoney());
			stmt.setString(5, agreement.getLandlordSign());

			return stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int updateTenant(String tenantSignPath, int agreementId) {
		final String sql = "UPDATE FORFUN.agreement SET TENANT_SIGN = ? WHERE AGREEMENT_ID = ?;";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, tenantSignPath);
			stmt.setInt(2, agreementId);
			return stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int selectAgmtidByOrderid(int orderId) {
		final String sql = "select AGREEMENT_ID from FORFUN.agreement where ORDER_ID = ? AND DELETE_TIME is null";
		
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();
			int agmtID = -1;
			while (rs.next()) {
				agmtID = rs.getInt("AGREEMENT_ID");
			}
			return agmtID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int selecOrderidByAgreementid(int agreementId) {
		final String sql = "select ORDER_ID from FORFUN.agreement where AGREEMENT_ID = ? AND DELETE_TIME is null";
		
		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, agreementId);
			ResultSet rs = pstmt.executeQuery();
			int agmtID = -1;
			while (rs.next()) {
				agmtID = rs.getInt("ORDER_ID");
			}
			return agmtID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteByOrderId(int orderId) {
		final String sql = "UPDATE FORFUN.agreement SET DELETE_TIME = ? WHERE ORDER_ID = ?;";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
			stmt.setInt(2, orderId);
			return stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Agreement selectByOrderId(int orderId) {
		final String sql = "SELECT * FROM FORFUN.agreement WHERE ORDER_ID = ? AND DELETE_TIME is null;";
		Agreement agreement=null;
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();			
			while (rs.next()) {
				agreement=new Agreement();
				agreement.setAgreementId(rs.getInt("AGREEMENT_ID"));
				agreement.setOrderId(rs.getInt("ORDER_ID"));
				agreement.setStartDate(rs.getTimestamp("START_DATE"));
				agreement.setEndDate(rs.getTimestamp("END_DATE"));
				agreement.setAgreementMoney(rs.getInt("AGREEMENT_MONEY"));
				agreement.setAgreementNote("AGREEMENT_NOTE");
				agreement.setLandlordSign(rs.getString("LANDLORD_SIGN"));
				agreement.setTenantSign(rs.getString("TENANT_SIGN"));
				agreement.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				agreement.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
			}
			return agreement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
