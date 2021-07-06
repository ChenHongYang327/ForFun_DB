package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import member.bean.Appointment;
import member.bean.Order;
import service.AppointmentService;
import service.OrderService;
import service.PublishService;

@WebServlet("/appoiment")
public class AppointmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        
        Gson gson = new Gson();
        
        try (
            BufferedReader reader = request.getReader();
            PrintWriter writer = response.getWriter();
        ) {
            AppointmentService appointmentService = new AppointmentService();
            OrderService orderService = new OrderService();
            
            JsonObject object = gson.fromJson(reader, JsonObject.class);
            String action = object.get("action").getAsString();
            
            if("makeAppointment".equals(action)) {
                // 預約看房
                Appointment appointment = gson.fromJson(object.get("appointment").getAsString(), Appointment.class);
                
                // 該資料存在進行update，否則insert
                int count = 0;
                if (appointmentService.selectById(appointment.getAppointmentId()) != null) {
                    appointment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    count = appointmentService.update(appointment);
                } else {
                    appointment.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    count = appointmentService.insert(appointment);
                }
                
                // 增加一筆訂單
                Order order = new Order();
                order.setPublishId(appointment.getPublishId());
                order.setTenantId(appointment.getTenantId());
                order.setOrderStatus(11);
                order.setRead(false);
                order.setCreateTime(new Timestamp(System.currentTimeMillis()));
                orderService.insert(order);
                
                JsonObject result = new JsonObject();
                result.addProperty("result_code", count > 0 ? 1 : 0);
                writer.write(gson.toJson(result));
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}