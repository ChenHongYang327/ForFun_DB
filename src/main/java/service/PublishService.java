package service;

import java.util.List;

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
}
