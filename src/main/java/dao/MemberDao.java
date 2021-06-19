package dao;

import java.util.List;

import member.bean.Member;




public interface MemberDao {

	int insert(Member member);
	
	int deleteById(int MEMBER_ID);
	
	int update(Member member);
	
	Member selectById(int MEMBER_ID);
	
	List<Member> selectAll();
	
	List<Integer> selectPhone();
	

}
