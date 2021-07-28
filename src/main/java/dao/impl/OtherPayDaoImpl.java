package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.OtherPayDao;
import member.bean.Order;
import member.bean.OtherPay;
import member.bean.Publish;

public class OtherPayDaoImpl implements OtherPayDao {

	private DataSource dataSource;

	public OtherPayDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public OtherPay selectById(int otherpayId) {
		final String sql = "SELECT * FROM FORFUN.otherpay WHERE OTHERPAY_ID = ? AND DELETE_TIME is null;";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, otherpayId);

			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					OtherPay otherPay = new OtherPay();
					otherPay.setOtherpayId(rs.getInt("OTHERPAY_ID"));
					otherPay.setAgreementId(rs.getInt("AGREEMENT_ID"));
					otherPay.setOtherpayMoney(rs.getInt("OTHERPAY_MONEY"));
					otherPay.setOtherpayNote(rs.getString("OTHERPAY_NOTE"));
					otherPay.setSuggestImg(rs.getString("SUGGEST_IMG"));
					otherPay.setOtherpayStatus(rs.getInt("OTHERPAY_STATUS"));
					otherPay.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					otherPay.setDeleteTime(rs.getTimestamp("DELETE_TIME"));

					return otherPay;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean changeOtherpayStatus(int otherpayID, int status) {
		final String sql = "UPDATE FORFUN.otherpay SET OTHERPAY_STATUS = ? WHERE OTHERPAY_ID = ? ;";

		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, status);
			stmt.setInt(2, otherpayID);

			stmt.executeUpdate();
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public int insert(OtherPay otherPay) {

		final String sql = "insert into FORFUN.otherpay (AGREEMENT_ID, OTHERPAY_MONEY, OTHERPAY_NOTE, SUGGEST_IMG) values (?, ?, ?, ?); ";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, otherPay.getAgreementId());
			stmt.setInt(2, otherPay.getOtherpayMoney());
			stmt.setString(3, otherPay.getOtherpayNote());
			stmt.setString(4, otherPay.getSuggestImg());

			return stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}


	@Override
	public List<OtherPay> selectByTenantId(int tenantId, int orderStaus, int otherpayStatus) {
		final String sql = "select ot.OTHERPAY_ID,ot.AGREEMENT_ID,ot.OTHERPAY_MONEY,ot.OTHERPAY_NOTE,ot.SUGGEST_IMG,ot.OTHERPAY_STATUS,ot.CREATE_TIME,ot.DELETE_TIME " + 
				"from FORFUN.order o left join FORFUN.agreement a on o.ORDER_ID = a.ORDER_ID left join FORFUN.otherpay ot on a.AGREEMENT_ID = ot.AGREEMENT_ID " + 
				"where o.TENANT_ID = ? AND o.ORDER_STATUS = ? AND ot.OTHERPAY_STATUS = ? AND o.DELETE_TIME is null;";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, tenantId);
			stmt.setInt(2, orderStaus);
			stmt.setInt(3, otherpayStatus);

			List<OtherPay> otherPays = new ArrayList<>();
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					OtherPay otherPay = new OtherPay();
					otherPay.setOtherpayId(rs.getInt("OTHERPAY_ID"));
					otherPay.setAgreementId(rs.getInt("AGREEMENT_ID"));
					otherPay.setOtherpayMoney(rs.getInt("OTHERPAY_MONEY"));
					otherPay.setOtherpayNote(rs.getString("OTHERPAY_NOTE"));
					otherPay.setSuggestImg(rs.getString("SUGGEST_IMG"));
					otherPay.setOtherpayStatus(rs.getInt("OTHERPAY_STATUS"));
					otherPay.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					otherPay.setDeleteTime(rs.getTimestamp("DELETE_TIME"));

					otherPays.add(otherPay);
				}
				return otherPays;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<OtherPay> selectByOwnerId(int ownerId, int orderStaus, int otherpayStatus) {
		final String sql = "select ot.* " + 
				"from FORFUN.order o "+ 
				"left join FORFUN.agreement a on o.ORDER_ID = a.ORDER_ID "+ 
				"left join FORFUN.otherpay ot on a.AGREEMENT_ID = ot.AGREEMENT_ID " +
				"left join FORFUN.publish p on o.PUBLISH_ID = p.PUBLISH_ID "+
				"where p.OWNER_ID = ? AND o.ORDER_STATUS = ? AND ot.OTHERPAY_STATUS = ? AND o.DELETE_TIME is null;";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, ownerId);
			stmt.setInt(2, orderStaus);
			stmt.setInt(3, otherpayStatus);

			List<OtherPay> otherPays = new ArrayList<>();
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					OtherPay otherPay = new OtherPay();
					otherPay.setOtherpayId(rs.getInt("OTHERPAY_ID"));
					otherPay.setAgreementId(rs.getInt("AGREEMENT_ID"));
					otherPay.setOtherpayMoney(rs.getInt("OTHERPAY_MONEY"));
					otherPay.setOtherpayNote(rs.getString("OTHERPAY_NOTE"));
					otherPay.setSuggestImg(rs.getString("SUGGEST_IMG"));
					otherPay.setOtherpayStatus(rs.getInt("OTHERPAY_STATUS"));
					otherPay.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					otherPay.setDeleteTime(rs.getTimestamp("DELETE_TIME"));

					otherPays.add(otherPay);
				}
				return otherPays;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int deleteByAgreementId(int agreementId) {
		final String sql = "UPDATE FORFUN.otherpay SET DELETE_TIME = ? WHERE AGREEMENT_ID = ?;";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
			stmt.setInt(2, agreementId);
			return stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
