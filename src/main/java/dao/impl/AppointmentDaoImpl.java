package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.AppointmentDao;
import member.bean.Appointment;

public class AppointmentDaoImpl implements AppointmentDao {
    private DataSource dataSource;
    private int insertId=-1;
    public AppointmentDaoImpl() {
        dataSource = ServiceLocator.getInstance().getDataSource();
    }

    @Override
    public int insert(Appointment appointment) {
        final String sql = "INSERT INTO appointment (PUBLISH_ID, OWNER_ID, TENANT_ID, APPOINTMENT_TIME, `READ`, CREATE_TIME) VALUES (?, ?, ?, ?, ?, ?);";
        
        try (
            Connection conn = dataSource.getConnection();
        	PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setInt(1, appointment.getPublishId());
            stmt.setInt(2, appointment.getOwnerId());
            stmt.setInt(3, appointment.getTenantId());
            stmt.setTimestamp(4, appointment.getAppointmentTime());
            stmt.setBoolean(5, appointment.getRead());
            stmt.setTimestamp(6, appointment.getCreateTime());
            int result = stmt.executeUpdate();
            if (result == 1) {
				ResultSet rs = stmt.getGeneratedKeys();
				while (rs.next()) {
					insertId = rs.getInt(1);
				}
			}
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public int deleteById(int appointmentId) {
        final String sql = "UPDATE appointment SET DELETE_TIME = ? WHERE APPOINTMENT_ID = ?;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, appointmentId);
            
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public int update(Appointment appointment) {
        final String sql = "UPDATE appointment SET PUBLISH_ID = ?, OWNER_ID = ?, TENANT_ID = ?, APPOINTMENT_TIME = ?, `READ` = ?, UPDATE_TIME = ? WHERE APPOINTMENT_ID = ?;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, appointment.getPublishId());
            stmt.setInt(2, appointment.getOwnerId());
            stmt.setInt(3, appointment.getTenantId());
            stmt.setTimestamp(4, appointment.getAppointmentTime());
            stmt.setBoolean(5, appointment.getRead());
            stmt.setTimestamp(6, appointment.getUpdateTime());
            stmt.setInt(7, appointment.getAppointmentId());
            
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public Appointment selectById(int appointmentId) {
        final String sql = "SELECT * FROM appointment WHERE APPOINTMENT_ID = ? and DELETE_TIME IS NULL;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, appointmentId);
            
            try (
                ResultSet rs = stmt.executeQuery();
            ) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(rs.getInt("APPOINTMENT_ID"));
                    appointment.setPublishId(rs.getInt("PUBLISH_ID"));
                    appointment.setOwnerId(rs.getInt("OWNER_ID"));
                    appointment.setTenantId(rs.getInt("TENANT_ID"));
                    appointment.setAppointmentTime(rs.getTimestamp("APPOINTMENT_TIME"));
                    appointment.setRead(rs.getBoolean("READ"));
                    appointment.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                    appointment.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
                    appointment.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
                    
                    return appointment;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<Appointment> selectAll() {
        final String sql = "SELECT * FROM appointment WHERE DELETE_TIME IS NULL;";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            List<Appointment> appointments = new ArrayList<Appointment>();
            
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("APPOINTMENT_ID"));
                appointment.setPublishId(rs.getInt("PUBLISH_ID"));
                appointment.setOwnerId(rs.getInt("OWNER_ID"));
                appointment.setTenantId(rs.getInt("TENANT_ID"));
                appointment.setAppointmentTime(rs.getTimestamp("APPOINTMENT_TIME"));
                appointment.setRead(rs.getBoolean("READ"));
                appointment.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                appointment.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
                appointment.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
                
                appointments.add(appointment);
            }
            
            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

	@Override
	public int getInsertId() {
		return insertId;
	}


	@Override
	public int selectAppointmentIdByTenantID(int publishId, int tenantId) {
	final String sql = "select APPOINTMENT_ID from FORFUN.appointment where PUBLISH_ID = ? AND TENANT_ID = ? AND DELETE_TIME IS NULL;";
		
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, publishId);
			pstmt.setInt(2, tenantId);
			
			ResultSet rs = pstmt.executeQuery();
			int apmtID = -1;
			while (rs.next()) {
				apmtID = rs.getInt("APPOINTMENT_ID");
			}
			return apmtID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int selectAppointmentIdByOwnerID(int publishId, int ownerId) {
	final String sql = "select APPOINTMENT_ID from FORFUN.appointment where PUBLISH_ID = ? AND OWNER_ID = ? AND DELETE_TIME IS NULL;";
		
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, publishId);
			pstmt.setInt(2, ownerId);
			
			ResultSet rs = pstmt.executeQuery();
			int apmtID = -1;
			while (rs.next()) {
				apmtID = rs.getInt("APPOINTMENT_ID");
			}
			return apmtID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
