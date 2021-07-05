package dao;

import java.util.List;

import member.bean.Area;

public interface AreaDao {
    List<Area> selectAll();
    
    String selectNameById(int areaId);
}
