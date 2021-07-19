package service;

import java.util.List;

import dao.MemberDao;
import dao.impl.MemberDaoImpl;
import member.bean.Member;

public class MemberService {
	MemberDao memberDao;

	public MemberService() {
		memberDao=new MemberDaoImpl();
	}
	
	public int insert(Member member) {
		return memberDao.insert(member);
	}
	
	
	public int deleteById(int MEMBER_ID) {
		return memberDao.deleteById(MEMBER_ID);
	}
	
	public int update(Member member) {
		return memberDao.update(member);
	}
	
	public int updateToken(Member member) {
		return memberDao.updateToken(member);
	}
	
	public int clearTokenById(int MEMBER_ID){
		return memberDao.clearTokenById(MEMBER_ID);
	}
	
	public Member selectById(int MEMBER_ID) {
		return memberDao.selectById(MEMBER_ID);
	}
	
	public List<Member> selectAll(){
		return memberDao.selectAll();
	}
	
	public List<Integer> selectAllPhone(){
		return memberDao.selectAllPhone();
	}
	
	public Member selectAllHeadShotAndName(int MEMBER_ID){
		return memberDao.selectAllHeadShotAndName(MEMBER_ID);
	}
}
