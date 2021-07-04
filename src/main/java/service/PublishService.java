package service;

import java.util.List;
import java.util.Map;

import dao.PublishDao;
import dao.impl.PublishDaoImpl;
import member.bean.Publish;

public class PublishService {

	private PublishDao dao;

	public PublishService() {
		dao = new PublishDaoImpl();
	}

	public int insert(Publish publish) {
		return dao.insert(publish);
	}

	public int deleteById(int publishId) {
		return dao.deleteById(publishId);
	}

	public int update(Publish publish) {
		return dao.update(publish);
	}

	public Publish selectById(int publishId) {
		return dao.selectById(publishId);
	}

	public List<Publish> selectAll() {
		return dao.selectAll();
	}

	public int getNewId() {
		return dao.getNewId();
	}

	public List<Publish> selectByOwnerId(int OWNER_ID) {
		return dao.selectByOwnerId(OWNER_ID);
	}

	// 用 刊登單ID 找 房東ID
	public int selectOwnerIdByID(int publishId) {
		return dao.selectOwnerIdByID(publishId);
	}

	public List<Publish> selectAllByParam(Map<String, String> paramMap) {
        return dao.selectAllByParam(paramMap);
    }
	
	public Publish selectByPublishID(int publishId) {
		return dao.selectById(publishId);
	}

}
