package com.userfront.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.userfront.DataAccessObject.AppointmentDao;
import com.userfront.domain.Appointment;
import com.userfront.service.AppointmentService;

public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired
	private AppointmentDao appointmentDao;
	
	public Appointment createAppointment(Appointment appointment) {
		return appointmentDao.save(appointment);
	}
	
	public List<Appointment> findAll() {
		return appointmentDao.findAll();
	}
	
	public Appointment findAppointment(Long id) {
		return appointmentDao.findOne(id);
	}
	
	public void confirmAppointment(Long id) { //Metod za prihvatanje appointment-a (ugovorenog sastanka) - Koristi admin
		Appointment appointment = findAppointment(id);
		appointment.setConfirmed(true);
		appointmentDao.save(appointment);
	}

}
