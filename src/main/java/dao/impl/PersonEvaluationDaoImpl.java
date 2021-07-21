package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.PersonEvaluationDao;
import member.bean.Member;
import member.bean.PersonEvaluation;

public class PersonEvaluationDaoImpl implements PersonEvaluationDao {
	DataSource dataSource;

	public PersonEvaluationDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(PersonEvaluation personEvaluation) {
		final String sql = "insert into FORFUN.person_evaluation(ORDER_ID, COMMENTED, COMMENTED_BY, PERSON_STAR, PERSON_COMMENT) values(?, ?, ?, ?, ?); ";

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setInt(1, personEvaluation.getOrderId());
			pstmt.setInt(2, personEvaluation.getCommented());
			pstmt.setInt(3, personEvaluation.getCommentedBy());
			pstmt.setInt(4, personEvaluation.getPersonStar());
			pstmt.setString(5, personEvaluation.getPersonComment());

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public int deleteById(int personEvaluation_ID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PersonEvaluation personEvaluation) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 取得評論此用戶的所有評價資料
	@Override
	public List<PersonEvaluation> selectByCommented(int COMMENTED) {
		final String sql = "select * from FORFUN.person_evaluation where COMMENTED = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, COMMENTED);
			ResultSet rs = pstmt.executeQuery();
			List<PersonEvaluation> personEvaluations = new ArrayList<>();
			while (rs.next()) {
				PersonEvaluation personEvaluation = new PersonEvaluation();
				personEvaluation.setPersonEvaluationId(rs.getInt("PERSON_EVALUATION_ID"));
				personEvaluation.setOrderId(rs.getInt("ORDER_ID"));
				personEvaluation.setCommented(rs.getInt("COMMENTED"));
				personEvaluation.setCommentedBy(rs.getInt("COMMENTED_BY"));
				personEvaluation.setPersonStar(rs.getInt("PERSON_STAR"));
				personEvaluation.setPersonComment(rs.getString("PERSON_COMMENT"));
				personEvaluation.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				personEvaluation.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				personEvaluation.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
				personEvaluations.add(personEvaluation);
			}

			return personEvaluations;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 取得評論者的用戶資料
	@Override
	public Member selectMemberByCommentId(int Commenter) {
		final String sql = "select * from FORFUN.member where MEMBER_ID = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, Commenter);
			ResultSet rs = pstmt.executeQuery();
			Member member = new Member();
			while (rs.next()) {
				member.setMemberId(rs.getInt("MEMBER_ID"));
				member.setRole(rs.getInt("ROLE"));
				member.setNameL(rs.getString("NAME_L"));
				member.setNameF(rs.getString("NAME_F"));
//				member.setPhone(rs.getInt("PHONE"));
				member.setHeadshot(rs.getString("HEADSHOT"));
				member.setGender(rs.getInt("GENDER"));
//				member.setId(rs.getString("ID"));
//				member.setBirthady(rs.getTimestamp("BIRTHDAY"));
				member.setAddress(rs.getString("ADDRESS"));
//				member.setMail(rs.getString("MAIL"));
//				member.setType(rs.getInt("TYPE"));
//				member.setToken(rs.getString("TOKEN"));
//				member.setIdImgf(rs.getString("ID_IMGF"));
//				member.setIdImgb(rs.getString("ID_IMGB"));
//				member.setCitizen(rs.getString("CITIZEN"));
				member.setCreateTime(rs.getTimestamp("CREATE_TIME"));

			}

			return member;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<PersonEvaluation> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEvaluationExist(int signinId, int orderId) {
		final String sql = "select CREATE_TIME from FORFUN.person_evaluation where COMMENTED_BY = ? AND ORDER_ID = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, signinId);
			pstmt.setInt(2, orderId);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
