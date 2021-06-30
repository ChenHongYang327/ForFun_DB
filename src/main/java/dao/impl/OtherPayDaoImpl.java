package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.OtherPayDao;
import member.bean.OtherPay;
import member.bean.Publish;

public class OtherPayDaoImpl implements OtherPayDao {

	private DataSource dataSource;

	public OtherPayDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public OtherPay selectById(int otherpayId) {
		final String sql = "SELECT * FROM FORFUN.otherpay WHERE OTHERPAY_ID = ? ;";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, otherpayId);

			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					OtherPay otherPay = new OtherPay();
					otherPay.setOtherpayId(rs.getInt("OTHERPAY_ID"));
					otherPay.setAgreementId(rs.getInt("AGREEMENT_ID"));
					otherPay.setOtherpayMoney(rs.getInt("OTHERPAY_MONEY"));
					otherPay.setOtherpayNote(rs.getNString("OTHERPAY_NOTE"));
					otherPay.setSuggestImg(rs.getNString("SUGGEST_IMG"));
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
		final String sql = "UPDATE otherpay SET OTHERPAY_STATUS = ? WHERE OTHERPAY_ID = ? ;";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, status);
			stmt.setInt(2, otherpayID);

			return stmt.execute();

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

}
