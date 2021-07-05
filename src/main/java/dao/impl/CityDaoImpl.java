package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.CityDao;
import member.bean.City;

public class CityDaoImpl implements CityDao {
    private DataSource dataSource;
    
    public CityDaoImpl() {
        dataSource = ServiceLocator.getInstance().getDataSource();
    }

    @Override
    public List<City> selectAll() {
        final String sql = "SELECT * FROM city WHERE DELETE_TIME IS NULL;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            List<City> cityList = new ArrayList<>();
            
            while (rs.next()) {
                City city = new City();
                city.setCityId(rs.getInt("CITY_ID"));
                city.setCityName(rs.getString("CITY_NAME"));
                city.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                city.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
                city.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
                
                cityList.add(city);
            }
            
            return cityList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
	public String selectNameById(int cityId) {
		final String sql = "SELECT CITY_NAME FROM FORFUN.city where CITY_ID=?";
		String cityName = null;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			stmt.setInt(1, cityId);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cityName=rs.getString("CITY_NAME");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityName;
	}
}
