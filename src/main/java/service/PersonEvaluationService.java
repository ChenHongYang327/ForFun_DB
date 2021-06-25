package service;

import java.util.List;

import dao.PersonEvaluationDao;
import dao.impl.PersonEvaluationDaoImpl;
import member.bean.Member;
import member.bean.PersonEvaluation;

public class PersonEvaluationService {
	PersonEvaluationDao pEvaluationDao;

	public PersonEvaluationService() {
		pEvaluationDao = new PersonEvaluationDaoImpl();
	}
	public int insert(PersonEvaluation personEvaluation) {
		return pEvaluationDao.insert(personEvaluation);
	}
	
	public int deleteById(int personEvaluation_ID) {
		return pEvaluationDao.deleteById(personEvaluation_ID);
	}
	
	public int update(PersonEvaluation personEvaluation) {
		return pEvaluationDao.update(personEvaluation);
	}
	
	public List<PersonEvaluation> selectByCommented(int COMMENTED){
		return pEvaluationDao.selectByCommented(COMMENTED);
	}
	
	public Member selectMemberByCommentId(int Comment_ID) {
		return pEvaluationDao.selectMemberByCommentId(Comment_ID);
	}
	
	public List<PersonEvaluation> selectAll(){
		return pEvaluationDao.selectAll();
	}
	
}
