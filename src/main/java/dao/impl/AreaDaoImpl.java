package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.AreaDao;
import member.bean.Area;

public class AreaDaoImpl implements AreaDao {
	private DataSource dataSource;

	public AreaDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public List<Area> selectAll() {
		final String sql = "SELECT * FROM area WHERE DELETE_TIME IS NULL;";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			List<Area> areaList = new ArrayList<>();

			while (rs.next()) {
				Area area = new Area();
				area.setAreaId(rs.getInt("AREA_ID"));
				area.setCityId(rs.getInt("CITY_ID"));
				area.setAreaName(rs.getString("AREA_NAME"));
				area.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				area.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				area.setDeleteTime(rs.getTimestamp("DELETE_TIME"));

				areaList.add(area);
			}

			return areaList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String selectNameById(int areaId) {
		final String sql = " SELECT AREA_NAME FROM FORFUN.area where AREA_ID = ? ";
		String areaName = null;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {
			stmt.setInt(1, areaId);
			while (rs.next()) {
				areaName = rs.getString("CITY_NAME");
			}

			return areaName;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
