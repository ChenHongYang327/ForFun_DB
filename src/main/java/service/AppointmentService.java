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
}
