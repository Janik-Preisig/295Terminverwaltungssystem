package com.example.terminsystem.service;

import com.example.terminsystem.model.Appointment;
import com.example.terminsystem.model.Customer;
import com.example.terminsystem.model.Employee;
import com.example.terminsystem.model.ServiceEntity;
import com.example.terminsystem.repository.AppointmentRepository;
import com.example.terminsystem.repository.CustomerRepository;
import com.example.terminsystem.repository.EmployeeRepository;
import com.example.terminsystem.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;
    private final CustomerRepository customerRepo;
    private final EmployeeRepository employeeRepo;
    private final ServiceRepository serviceRepo;

    public AppointmentService(AppointmentRepository repo,
                              CustomerRepository customerRepo,
                              EmployeeRepository employeeRepo,
                              ServiceRepository serviceRepo) {
        this.repo = repo;
        this.customerRepo = customerRepo;
        this.employeeRepo = employeeRepo;
        this.serviceRepo = serviceRepo;
    }

    public Appointment createAppointment(Long customerId, Long employeeId, Long serviceId, String dateStr) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        ServiceEntity service = serviceRepo.findById(serviceId).orElseThrow();

        // Prüfen, ob Mitarbeiter den Service anbietet
        if (!employee.getServices().contains(service)) {
            throw new RuntimeException("Mitarbeiter bietet diesen Service nicht an");
        }

        LocalDateTime date = LocalDateTime.parse(dateStr);

        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setEmployee(employee);
        appointment.setService(service);
        appointment.setDate(date);
        appointment.setStatus("ACTIVE");

        return repo.save(appointment);
    }

    public List<Appointment> getAppointments() {
        return repo.findAll();
    }

    public List<Appointment> getAppointmentsByCustomer(Long customerId) {
        return repo.findByCustomerId(customerId);
    }

    public List<Appointment> getAppointmentsByEmployee(Long employeeId) {
        return repo.findByEmployeeId(employeeId);
    }

    public Appointment cancelAppointment(Long id) {
        Appointment appointment = repo.findById(id).orElseThrow();
        appointment.setStatus("CANCELLED");
        return repo.save(appointment);
    }
}
