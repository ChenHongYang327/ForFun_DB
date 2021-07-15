package dao;

import java.util.List;
import java.util.Map;

import member.bean.Publish;

public interface PublishDao {
	int insert(Publish publish);

	int deleteById(int publishId);

	int update(Publish publish);
	
	int updateStatus(int status,int publishId);

	Publish selectById(int publishId);

	List<Publish> selectByOwnerId(int OWNER_ID);

	List<Publish> selectAll();

	int getNewId();

	// 用 刊登單ID 找 房東ID
	int selectOwnerIdByID (int publishId);

	List<Publish> selectAllByParam(Map<String, String> paramMap);
}
