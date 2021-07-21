package dao;

import java.util.List;

import member.bean.Member;
import member.bean.PersonEvaluation;




public interface PersonEvaluationDao {
	int insert(PersonEvaluation personEvaluation);
	
	int deleteById(int personEvaluation_ID);
	
	int update(PersonEvaluation personEvaluation);
	
	List<PersonEvaluation> selectByCommented(int COMMENTED); 
	
	Member selectMemberByCommentId(int Comment_ID);
	
	List<PersonEvaluation> selectAll();
	
	boolean isEvaluationExist(int signinId, int orderId);
	
}
