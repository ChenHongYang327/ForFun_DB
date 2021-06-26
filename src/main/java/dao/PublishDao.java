package dao;

import java.util.List;

import member.bean.Publish;

public interface PublishDao {
    int insert(Publish publish);
    
    int deleteById(int publishId);
    
    int update(Publish publish);
    
    Publish selectById(int publishId);
    
    List<Publish> selectByOwnerId(int OWNER_ID);
    
    List<Publish> selectAll();
    
    int getNewId();
}
