package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import member.bean.Member;

public class MemberDaoImpl implements MemberDao {
	DataSource dataSource;

	public MemberDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(Member member) {
		final String sql = "insert into Member (ROLE,NAME_L,NAME_F,PHONE,HEADSHOT,GENDER,ID,BIRTHDAY,ADDRESS,MAIL,TYPE,TOKEN,ID_IMGF,ID_IMGB,CITIZEN,CREATE_TIME,UPDATE_TIME) values (?,?,?,?,?,?,?,?,?,?,?) ";
		return 0;
	}

	@Override
	public int deleteById(int MEMBER_ID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Member member) {
		final String sql;
		if (member.getCitizen()==null) {
			sql = "UPDATE FORFUN.member SET NAME_L = ?, NAME_F = ?, PHONE = ?, HEADSHOT =?, ADDRESS =?, MAIL =?, UPDATE_TIME =? WHERE MEMBER_ID =?";
		} else {
			sql = "UPDATE FORFUN.member SET NAME_L = ?, NAME_F = ?, PHONE = ?, HEADSHOT =?, ADDRESS =?, MAIL =?, CITIZEN =?, UPDATE_TIME =? WHERE MEMBER_ID =?";
		}
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			if (member.getCitizen()==null) {
					pstmt.setString(1, member.getNameL());
					pstmt.setString(2, member.getNameF());
					pstmt.setInt(3, member.getPhone());
					pstmt.setString(4, member.getHeadshot());
					pstmt.setString(5, member.getAddress());
					pstmt.setString(6, member.getMail());
					pstmt.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
					pstmt.setInt(8,member.getMemberId());
			} else {
				pstmt.setString(1, member.getNameL());
				pstmt.setString(2, member.getNameF());
				pstmt.setInt(3, member.getPhone());
				pstmt.setString(4, member.getHeadshot());
				pstmt.setString(5, member.getAddress());
				pstmt.setString(6, member.getMail());
				pstmt.setString(7, member.getCitizen());
				pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
				pstmt.setInt(9,member.getMemberId());
			}
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Member selectById(int MEMBER_ID) {
		final String sql = "select * from FORFUN.member where MEMBER_ID = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setInt(1, MEMBER_ID);
			ResultSet rs = pstmt.executeQuery();

			Member member = new Member();
			while (rs.next()) {
				member.setMemberId(rs.getInt("MEMBER_ID"));
				;
				member.setRole(rs.getInt("ROLE"));
				member.setNameL(rs.getString("NAME_L"));
				member.setNameF(rs.getString("NAME_F"));
				member.setPhone(rs.getInt("PHONE"));
				member.setHeadshot(rs.getString("HEADSHOT"));
				member.setGender(rs.getInt("GENDER"));
				member.setId(rs.getString("ID"));
				member.setBirthady(rs.getTimestamp("BIRTHDAY"));
				member.setAddress(rs.getString("ADDRESS"));
				member.setMail(rs.getString("MAIL"));
				member.setType(rs.getInt("TYPE"));
				member.setToken(rs.getString("TOKEN"));
				member.setIdImgf(rs.getString("ID_IMGF"));
				member.setIdImgb(rs.getString("ID_IMGB"));
				member.setCitizen(rs.getString("CITIZEN"));
				member.setCreateTime(rs.getTimestamp("CREATE_TIME"));

			}
			return member;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Member> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
