package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.PublishDao;
import member.bean.Publish;

public class PublishDaoImpl implements PublishDao {
    private DataSource dataSource;
    
    public PublishDaoImpl() {
        dataSource = ServiceLocator.getInstance().getDataSource();
    }
    
    @Override
    public int insert(Publish publish) {
        final String sql = "INSERT INTO publish (OWNER_ID, TITLE, TITLE_IMG, PUBLISH_INFO, PUBLISH_IMG1, PUBLISH_IMG2, PUBLISH_IMG3, CITY_ID, AREA_ID, ADDRESS, LATITUDE, LONGITUDE, RENT, DEPOSIT, SQUARE, GENDER, TYPE, FURNISHED, CREATE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, publish.getOwnerId());
            stmt.setString(2, publish.getTitle());
            stmt.setString(3, publish.getTitleImg());
            stmt.setString(4, publish.getPublishInfo());
            stmt.setString(5, publish.getPublishImg1());
            stmt.setString(6, publish.getPublishImg2());
            stmt.setString(7, publish.getPublishImg3());
            stmt.setInt(8, publish.getCityId());
            stmt.setInt(9, publish.getAreaId());
            stmt.setString(10, publish.getAddress());
            stmt.setDouble(11, publish.getLatitude());
            stmt.setDouble(12, publish.getLongitude());
            stmt.setInt(13, publish.getRent());
            stmt.setInt(14, publish.getDeposit());
            stmt.setInt(15, publish.getSquare());
            stmt.setInt(16, publish.getGender());
            stmt.setInt(17, publish.getType());
            stmt.setString(18, publish.getFurnished());
            stmt.setTimestamp(19, publish.getCreateTime());
            
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteById(int publishId) {
        final String sql = "UPDATE publish SET DELETE_TIME = ? WHERE PUBLISH_ID = ?;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, publishId);
            
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public int update(Publish publish) {
        final String sql = "UPDATE publish SET OWNER_ID = ?, TITLE = ?, TITLE_IMG = ?, PUBLISH_INFO = ?, PUBLISH_IMG1 = ?, PUBLISH_IMG2 = ?, PUBLISH_IMG3 = ?, CITY_ID = ?, AREA_ID = ?, ADDRESS = ?, LATITUDE = ?, LONGITUDE = ?, RENT = ?, DEPOSIT = ?, SQUARE = ?, GENDER = ?, TYPE = ?, FURNISHED = ?, UPDATE_TIME = ? WHERE PUBLISH_ID = ?;";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, publish.getOwnerId());
            stmt.setString(2, publish.getTitle());
            stmt.setString(3, publish.getTitleImg());
            stmt.setString(4, publish.getPublishInfo());
            stmt.setString(5, publish.getPublishImg1());
            stmt.setString(6, publish.getPublishImg2());
            stmt.setString(7, publish.getPublishImg3());
            stmt.setInt(8, publish.getCityId());
            stmt.setInt(9, publish.getAreaId());
            stmt.setString(10, publish.getAddress());
            stmt.setDouble(11, publish.getLatitude());
            stmt.setDouble(12, publish.getLongitude());
            stmt.setInt(13, publish.getRent());
            stmt.setInt(14, publish.getDeposit());
            stmt.setInt(15, publish.getSquare());
            stmt.setInt(16, publish.getGender());
            stmt.setInt(17, publish.getType());
            stmt.setString(18, publish.getFurnished());
            stmt.setTimestamp(19, publish.getUpdateTime());
            stmt.setInt(20, publish.getPublishId());
            
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public Publish selectById(int publishId) {
        final String sql = "SELECT * FROM FORFUN.publish WHERE PUBLISH_ID = ? and DELETE_TIME IS NULL;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, publishId);
            
            try (
                ResultSet rs = stmt.executeQuery();
            ) {
                while (rs.next()) {
                    Publish publish = new Publish();
                    publish.setPublishId(rs.getInt("PUBLISH_ID"));
                    publish.setOwnerId(rs.getInt("OWNER_ID"));
                    publish.setTitle(rs.getString("TITLE"));
                    publish.setTitleImg(rs.getString("TITLE_IMG"));
                    publish.setPublishInfo(rs.getString("PUBLISH_INFO"));
                    publish.setPublishImg1(rs.getString("PUBLISH_IMG1"));
                    publish.setPublishImg2(rs.getString("PUBLISH_IMG2"));
                    publish.setPublishImg3(rs.getString("PUBLISH_IMG3"));
                    publish.setCityId(rs.getInt("CITY_ID"));
                    publish.setAreaId(rs.getInt("AREA_ID"));
                    publish.setAddress(rs.getString("ADDRESS"));
                    publish.setLatitude(rs.getDouble("LATITUDE"));
                    publish.setLongitude(rs.getDouble("LONGITUDE"));
                    publish.setRent(rs.getInt("RENT"));
                    publish.setDeposit(rs.getInt("DEPOSIT"));
                    publish.setSquare(rs.getInt("SQUARE"));
                    publish.setGender(rs.getInt("GENDER"));
                    publish.setType(rs.getInt("TYPE"));
                    publish.setFurnished(rs.getString("FURNISHED"));
                    publish.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                    publish.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
                    publish.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
                    
                    return publish;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<Publish> selectAll() {
        final String sql = "SELECT * FROM publish WHERE DELETE_TIME IS NULL;";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            List<Publish> pibList = new ArrayList<Publish>();
            
            while (rs.next()) {
                Publish publish = new Publish();
                publish.setPublishId(rs.getInt("PUBLISH_ID"));
                publish.setOwnerId(rs.getInt("OWNER_ID"));
                publish.setTitle(rs.getString("TITLE"));
                publish.setTitleImg(rs.getString("TITLE_IMG"));
                publish.setPublishInfo(rs.getString("PUBLISH_INFO"));
                publish.setPublishImg1(rs.getString("PUBLISH_IMG1"));
                publish.setPublishImg2(rs.getString("PUBLISH_IMG2"));
                publish.setPublishImg3(rs.getString("PUBLISH_IMG3"));
                publish.setCityId(rs.getInt("CITY_ID"));
                publish.setAreaId(rs.getInt("AREA_ID"));
                publish.setAddress(rs.getString("ADDRESS"));
                publish.setLatitude(rs.getDouble("LATITUDE"));
                publish.setLongitude(rs.getDouble("LONGITUDE"));
                publish.setRent(rs.getInt("RENT"));
                publish.setDeposit(rs.getInt("DEPOSIT"));
                publish.setSquare(rs.getInt("SQUARE"));
                publish.setGender(rs.getInt("GENDER"));
                publish.setType(rs.getInt("TYPE"));
                publish.setFurnished(rs.getString("FURNISHED"));
                publish.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                publish.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
                publish.setDeleteTime(rs.getTimestamp("DELETE_TIME"));

                pibList.add(publish);
            }
            
            return pibList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int getNewId() {
        final String sql = "INSERT INTO publish (OWNER_ID, CREATE_TIME) VALUES (?, ?);";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"PUBLISH_ID"}); // 指定自動增值的欄位名稱
        ) {
            stmt.setInt(1, 1);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            
            if (stmt.executeUpdate() > 0) {
                try (
                    ResultSet rs = stmt.getGeneratedKeys();
                ) {
                    if (rs.next()) {
                        return rs.getInt("GENERATED_KEY");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

	@Override
	public List<Publish> selectByOwnerId(int OWNER_ID) {
		final String sql = "select * from FORFUN.publish where OWNER_ID=? order by DELETE_TIME ASC";
		List<Publish> publishs = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			stmt.setInt(1, OWNER_ID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Publish publish=new Publish();
				publish.setPublishId(rs.getInt("PUBLISH_ID"));
				publish.setOwnerId(rs.getInt("OWNER_ID"));
				publish.setTitle(rs.getString("TITLE"));
				publish.setTitleImg(rs.getString("TITLE_IMG"));
				publish.setPublishInfo(rs.getString("PUBLISH_INFO"));
				publish.setPublishImg1(rs.getString("PUBLISH_IMG1"));
				publish.setPublishImg2(rs.getString("PUBLISH_IMG2"));
				publish.setPublishImg3(rs.getString("PUBLISH_IMG3"));
				publish.setCityId(rs.getInt("CITY_ID"));
				publish.setAreaId(rs.getInt("AREA_ID"));
				publish.setAddress(rs.getString("ADDRESS"));
				publish.setLatitude(rs.getDouble("LATITUDE"));
				publish.setLongitude(rs.getDouble("LONGITUDE"));
				publish.setRent(rs.getInt("RENT"));
				publish.setDeposit(rs.getInt("DEPOSIT"));
				publish.setSquare(rs.getInt("SQUARE"));
				publish.setGender(rs.getInt("GENDER"));
				publish.setType(rs.getInt("TYPE"));
				publish.setFurnished(rs.getString("FURNISHED"));
				publish.setCreateTime(rs.getTimestamp("CREATE_TIME"));
				publish.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
				publish.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
				publishs.add(publish);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return publishs;
	}

	@Override
	public int selectOwnerIdByID(int publishId) {
		final String sql = "select OWNER_ID from FORFUN.publish where PUBLISH_ID = ?";
		try (Connection conn = dataSource.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, publishId);
			ResultSet rs = pstmt.executeQuery();
			int ownerID = -1;
			while (rs.next()) {
				ownerID = rs.getInt("OWNER_ID");
			}
			return ownerID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
