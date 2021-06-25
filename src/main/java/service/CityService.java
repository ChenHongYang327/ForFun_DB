package service;

import java.util.List;

import dao.CityDao;
import dao.impl.CityDaoImpl;
import member.bean.City;

public class CityService {
    private CityDao dao;
    
    public CityService() {
        dao = new CityDaoImpl();
    }
    
    public List<City> selectAll() {
        return dao.selectAll();
    }
    
    public String selectNameById(int cityId) {
    	return dao.selectNameById(cityId);
    }
    
}
