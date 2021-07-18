package dao;

import java.util.List;

import member.bean.Appointment;

public interface AppointmentDao {
    int insert(Appointment appointment);
    
    int deleteById(int appointmentId);
    
    int update(Appointment appointment);
    
    Appointment selectById(int appointmentId);
    
    List<Appointment> selectAll();
    
    int getInsertId();
    
    int selectAppointmentIdByTenantID(int publishId,int tenantId);
    
    int selectAppointmentIdByOwnerID(int publishId,int ownerId);
}
