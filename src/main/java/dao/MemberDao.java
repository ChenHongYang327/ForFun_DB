package dao;

import java.util.List;

import member.bean.Member;




public interface MemberDao {

	int insert(Member member);
	
	int deleteById(int MEMBER_ID);
	
	int update(Member member);
	
	int adminUpdate(Member member);
	
	int adminUpdatePass(Member member);
	
	int updateToken(Member member);
	
	
	int clearTokenById(int MEMBER_ID);
	
	Member selectById(int MEMBER_ID);
	
	List<Member> selectAll();
	
	List<Integer> selectAllPhone();
	
	Member selectByPhone(int phone);

	Member selectAllHeadShotAndName(int MEMBER_ID);
	
	List<Member> selectApplyLandlordMember();
	

}
