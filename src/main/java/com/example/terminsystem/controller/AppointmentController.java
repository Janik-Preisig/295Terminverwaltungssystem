package com.example.terminsystem.controller;

import com.example.terminsystem.model.Appointment;
import com.example.terminsystem.service.AppointmentService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@Valid @RequestBody AppointmentRequest request) {
        Appointment created = appointmentService.createAppointment(
                request.getCustomerId(),
                request.getEmployeeId(),
                request.getServiceId(),
                request.getDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAll(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long employeeId) {

        List<Appointment> appointments;
        if (customerId != null) {
            appointments = appointmentService.getAppointmentsByCustomer(customerId);
        } else if (employeeId != null) {
            appointments = appointmentService.getAppointmentsByEmployee(employeeId);
        } else {
            appointments = appointmentService.getAppointments();
        }
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancel(@PathVariable Long id) {
        Appointment cancelled = appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(cancelled);
    }

    public static class AppointmentRequest {
        @NotNull(message = "CustomerId darf nicht leer sein")
        private Long customerId;

        @NotNull(message = "EmployeeId darf nicht leer sein")
        private Long employeeId;

        @NotNull(message = "ServiceId darf nicht leer sein")
        private Long serviceId;

        @NotNull(message = "Datum darf nicht leer sein")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Einfacheres Format für Postman
        private LocalDateTime date;

        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public Long getEmployeeId() { return employeeId; }
        public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
        public Long getServiceId() { return serviceId; }
        public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
        public LocalDateTime getDate() { return date; }
        public void setDate(LocalDateTime date) { this.date = date; }
    }
}