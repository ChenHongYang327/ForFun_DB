package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import commend.ServiceLocator;
import dao.NotificationDao;
import member.bean.Notification;

public class NotificationImpl implements NotificationDao {
	DataSource dataSource;

	public NotificationImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insertComment(int notifiedId, int commentId) {
		final String sql = "INSERT into FORFUN.notification (NOTIFIED_ID,COMMENT_ID,CREATE_TIME) values (?,?,?);";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, notifiedId);
			pstmt.setInt(2, commentId);
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int inserAppointment(int notifiedId, int appointmenId) {
		final String sql = "INSERT into FORFUN.notification (NOTIFIED_ID,APPOINTMENT_ID,CREATE_TIME) values (?,?,?);";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, notifiedId);
			pstmt.setInt(2, appointmenId);
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int insertOreder(int notifiedId, int orederId) {
		final String sql = "INSERT into FORFUN.notification (NOTIFIED_ID,ORDER_ID,CREATE_TIME) values (?,?,?);";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, notifiedId);
			pstmt.setInt(2, orederId);
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int insertMessage(int notifiedId, int messageId) {
		final String sql = "INSERT into FORFUN.notification (NOTIFIED_ID,MESSAGE_ID,CREATE_TIME) values (?,?,?);";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, notifiedId);
			pstmt.setInt(2, messageId);
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int upadteComment(int notifiedId, int commentId) {
		final String sql = "UPDATE FORFUN.notification SET DELETE_TIME=? WHERE NOTIFIED_ID =? and COMMENT_ID=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(2, notifiedId);
			pstmt.setInt(3, commentId);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public int upadteAppointment(int notifiedId, int appointmenId) {
		final String sql = "UPDATE FORFUN.notification SET DELETE_TIME=? WHERE NOTIFIED_ID =? and APPOINTMENT_ID=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(2, notifiedId);
			pstmt.setInt(3, appointmenId);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public int upadteOreder(int notifiedId, int orederId) {
		final String sql = "UPDATE FORFUN.notification SET DELETE_TIME=? WHERE NOTIFIED_ID =? and ORDER_ID=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(2, notifiedId);
			pstmt.setInt(3, orederId);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public int upadteMessage(int notifiedId, int messageId) {
		final String sql = "UPDATE FORFUN.notification SET DELETE_TIME=? WHERE NOTIFIED_ID =? and MESSAGE_ID=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(2, notifiedId);
			pstmt.setInt(3, messageId);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<Notification> selectByMemberID(int notifiedId) {
		final String sql = "select * from FORFUN.notification where NOTIFIED_ID = ? and DELETE_TIME is null order by CREATE_TIME DESC";
		List<Notification> notifications=new ArrayList<Notification>();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, notifiedId);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				Notification notification=new Notification();
				notification.setNotificationId(rs.getInt("NOTIFICATION_ID"));
				notification.setNotified(rs.getInt("NOTIFIED_ID"));
				notification.setCommentId(rs.getInt("COMMENT_ID"));
				notification.setAppointmentId(rs.getInt("APPOINTMENT_ID"));
				notification.setOrderId(rs.getInt("ORDER_ID"));
				notification.setMessageId(rs.getInt("MESSAGE_ID"));
				notification.setRead(rs.getBoolean("READ"));
				notification.setCreateTime(rs.getTimestamp("CREATE_TIME"));
//				notification.setDeleteTime(rs.getTimestamp("DELETE_TIME"));
				notifications.add(notification);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
		return notifications;
	}

	@Override
	public int updateReaded(int notifiedId) {
		final String sql = "UPDATE FORFUN.notification SET READ=?,DELETE_TIME=? WHERE NOTIFIED_ID =?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setBoolean(1, true);
			pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(3, notifiedId);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
