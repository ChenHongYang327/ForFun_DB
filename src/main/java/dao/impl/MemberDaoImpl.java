package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.MemberDao;
import member.bean.Member;

public class MemberDaoImpl implements MemberDao {
	DataSource dataSource;

	public MemberDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(Member member) {
		final String sql;
		if(member.getCitizen()==null) {
			sql= "insert into Member (ROLE,NAME_L,NAME_F,PHONE,"
					+ "HEADSHOT,GENDER,ID,BIRTHDAY,"
					+ "ADDRESS,MAIL,TYPE,TOKEN,"
					+ "ID_IMGF,ID_IMGB,CREATE_TIME) "
					+ "values (?,?,?,?,"
					+ "?,?,?,?"
					+ ",?,?,?,?,"
					+ "?,?,?) ";
		}
		else {
			sql= "insert into Member (ROLE,NAME_L,NAME_F,PHONE,"
					+ "HEADSHOT,GENDER,ID,BIRTHDAY,"
					+ "ADDRESS,MAIL,TYPE,TOKEN,"
					+ "ID_IMGF,ID_IMGB,CITIZEN,CREATE_TIME) "
					+ "values (?,?,?,?,"
					+ "?,?,?,?"
					+ ",?,?,?,?,"
					+ "?,?,?,?) ";
		}
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			if(member.getCitizen()==null) {
				pstmt.setInt(1, member.getRole()); 
				pstmt.setString(2, member.getNameL());
				pstmt.setString(3, member.getNameF());
				pstmt.setInt(4, member.getPhone());
				pstmt.setString(5, member.getHeadshot());
				pstmt.setInt(6, member.getGender());
				pstmt.setString(7, member.getId());
				pstmt.setTimestamp(8, member.getBirthady());
				pstmt.setString(9, member.getAddress());
				pstmt.setString(10, member.getMail());				
				pstmt.setInt(11, member.getType());
				pstmt.setString(12, member.getToken());
				pstmt.setString(13, member.getIdImgf());
				pstmt.setString(14, member.getIdImgb());
				pstmt.setTimestamp(15,new Timestamp(System.currentTimeMillis()));
				return pstmt.executeUpdate();
			}
			else {
				pstmt.setInt(1, member.getRole()); 
				pstmt.setString(2, member.getNameL());
				pstmt.setString(3, member.getNameF());
				pstmt.setInt(4, member.getPhone());
				pstmt.setString(5, member.getHeadshot());
				pstmt.setInt(6, member.getGender());
				pstmt.setString(7, member.getId());
				pstmt.setTimestamp(8, member.getBirthady());
				pstmt.setString(9, member.getAddress());
				pstmt.setString(10, member.getMail());				
				pstmt.setInt(11, member.getType());
				pstmt.setString(12, member.getToken());
				pstmt.setString(13, member.getIdImgf());
				pstmt.setString(14, member.getIdImgb());
				pstmt.setString(15, member.getCitizen());
				pstmt.setTimestamp(16,new Timestamp(System.currentTimeMillis()));
				return pstmt.executeUpdate();
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
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
	public int updateToken(Member member) {
		final String sql="UPDATE FORFUN.member SET TOKEN = ? WHERE MEMBER_ID =?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, member.getToken());
			pstmt.setInt(2, member.getMemberId());
			return pstmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public int adminUpdate(Member member) {
		final String sql= "UPDATE FORFUN.member SET PHONE =?,TYPE =?,ROLE =? WHERE MEMBER_ID =?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, member.getPhone());
			pstmt.setInt(2, member.getType());
			pstmt.setInt(3, member.getRole());
			pstmt.setInt(4, member.getMemberId());
			return pstmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public int clearTokenById(int MEMBER_ID) {
		final String sql="UPDATE FORFUN.member SET TOKEN = NULL WHERE MEMBER_ID =?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, MEMBER_ID);
			return pstmt.executeUpdate();
		}
		catch (Exception e) {
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
			Member member = null;
			
			while (rs.next()) {
				member=new Member();
				member.setMemberId(rs.getInt("MEMBER_ID"));
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
				member.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				member.setDeleteTime(rs.getTimestamp("DELETE_TIME"));

			}
			return member;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Member> selectAll() {
		final String sql = "select * from FORFUN.member";
		List<Member> members=new ArrayList<Member>();
		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql); 
			ResultSet rs = pstmt.executeQuery()){
			while(rs.next()) {
				Member member=new Member();
				member.setMemberId(rs.getInt("MEMBER_ID"));
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
				member.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				member.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
				members.add(member);
			}
			return members;
					
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Integer> selectAllPhone() {
		final String sql = "select PHONE from FORFUN.member";
		List<Integer> phones=null;
		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				phones=new ArrayList<>();
				phones.add(rs.getInt(1));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return phones;
	}

	@Override
	public int deleteById(int MEMBER_ID) {
		// TODO Auto-generated method stub
		return 0;
	}
           
	
	@Override
	public Member selectAllHeadShotAndName(int MEMBER_ID) {
		final String sql = "select * from FORFUN.member where MEMBER_ID = ?";
		
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setInt(1, MEMBER_ID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getInt("MEMBER_ID"));
				member.setNameL(rs.getString("NAME_L"));
				member.setNameF(rs.getString("NAME_F"));
				member.setHeadshot(rs.getString("HEADSHOT"));
				return member;

			}
	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	@Override
	public List<Member> selectApplyLandlordMember() {
		final String sql = "select * from FORFUN.member WHERE ROLE=1 and CITIZEN is not null";
		List<Member> members=new ArrayList<Member>();
		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql); 
			ResultSet rs = pstmt.executeQuery()){
			while(rs.next()) {
				Member member=new Member();
				member.setMemberId(rs.getInt("MEMBER_ID"));
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
				member.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				member.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
				members.add(member);
			}
			return members;
					
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	

	
	@Override
	public Member selectByPhone(int phone) {
		final String sql = "select * from FORFUN.member where PHONE = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setInt(1, phone);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member=new Member();
				member.setMemberId(rs.getInt("MEMBER_ID"));
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
				member.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				member.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
				return member;

			}
	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int adminUpdatePass(Member member) {
		final String sql= "UPDATE FORFUN.member SET ROLE =?, CITIZEN =? WHERE MEMBER_ID =?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, member.getRole());
			pstmt.setString(2, member.getCitizen());
			pstmt.setInt(3, member.getMemberId());
			
			return pstmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	

}
