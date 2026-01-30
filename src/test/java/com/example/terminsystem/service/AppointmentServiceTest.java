package com.example.terminsystem.service;

import com.example.terminsystem.model.*;
import com.example.terminsystem.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock private AppointmentRepository repo;
    @Mock private CustomerRepository customerRepo;
    @Mock private EmployeeRepository employeeRepo;
    @Mock private ServiceRepository serviceRepo;

    @InjectMocks
    private AppointmentService appointmentService;

    private Customer customer;
    private Employee employee;
    private ServiceEntity service;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        service = new ServiceEntity();
        service.setId(10L);
        service.setName("Haarschnitt");

        employee = new Employee();
        employee.setId(5L);
        employee.setServices(Set.of(service)); // Mitarbeiter bietet den Service an
    }

    @Test
    void createAppointment_Success() {
        // Arrange
        String dateStr = "2026-05-10T10:00:00";
        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(employeeRepo.findById(5L)).thenReturn(Optional.of(employee));
        when(serviceRepo.findById(10L)).thenReturn(Optional.of(service));
        when(repo.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Appointment result = appointmentService.createAppointment(1L, 5L, 10L, dateStr);

        // Assert
        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
        assertEquals(customer, result.getCustomer());
        verify(repo, times(1)).save(any());
    }

    @Test
    void createAppointment_ThrowsException_WhenServiceNotOfferedByEmployee() {
        // Arrange
        ServiceEntity otherService = new ServiceEntity();
        otherService.setId(99L);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(employeeRepo.findById(5L)).thenReturn(Optional.of(employee));
        when(serviceRepo.findById(99L)).thenReturn(Optional.of(otherService));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            appointmentService.createAppointment(1L, 5L, 99L, "2026-05-10T10:00:00");
        });
    }
}