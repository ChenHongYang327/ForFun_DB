package service;

import java.util.List;

import dao.AppointmentDao;
import dao.impl.AppointmentDaoImpl;
import member.bean.Appointment;

public class AppointmentService {
    AppointmentDao dao;
    
    public AppointmentService() {
        dao = new AppointmentDaoImpl();
    }
    
    public int insert(Appointment appointment) {
        return dao.insert(appointment);
    }

    public int deleteById(int appointmentId) {
        return dao.deleteById(appointmentId);
    }

    public int update(Appointment appointment) {
        return dao.update(appointment);
    }

    public Appointment selectById(int appointmentId) {
        return dao.selectById(appointmentId);
    }

    public List<Appointment> selectAll() {
        return dao.selectAll();
    }
    //取得insert的Id失敗會得到-1
    public int getInsertId() {
    	return dao.getInsertId();
    }
    
    public int selectAppointmentIdByTenantID(int publishId,int tenantId) {
    	return dao.selectAppointmentIdByTenantID(publishId, tenantId);
    }
    
    public int selectAppointmentIdByOwnerID(int publishId,int ownerId) {
    	return dao.selectAppointmentIdByOwnerID(publishId, ownerId);
    }
    //透過刊登單Id找出所有預約單
    public List<Appointment> selectAppointmentByPublishId(int publishId){
    	return dao.selectAppointmentByPublishId(publishId);
    }
}
