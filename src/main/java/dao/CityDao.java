package dao;

import java.util.List;

import member.bean.City;

public interface CityDao {
    List<City> selectAll();
}
