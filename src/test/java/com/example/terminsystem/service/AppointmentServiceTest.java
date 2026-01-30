package com.example.terminsystem.service;

import com.example.terminsystem.model.*;
import com.example.terminsystem.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.of(2026, 5, 10, 10, 0, 0);

        customer = new Customer();
        customer.setId(1L);

        service = new ServiceEntity();
        service.setId(10L);
        service.setName("Haarschnitt");

        employee = new Employee();
        employee.setId(5L);
        // Wichtig: Der Mitarbeiter muss den Service im Set haben
        employee.setServices(Set.of(service));
    }

    @Test
    void createAppointment_Success() {
        // Arrange
        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(employeeRepo.findById(5L)).thenReturn(Optional.of(employee));
        when(serviceRepo.findById(10L)).thenReturn(Optional.of(service));
        when(repo.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        // Hier wird nun direkt das LocalDateTime Objekt übergeben
        Appointment result = appointmentService.createAppointment(1L, 5L, 10L, testDate);

        // Assert
        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
        assertEquals(customer, result.getCustomer());
        assertEquals(employee, result.getEmployee());
        assertEquals(testDate, result.getDate());

        verify(repo, times(1)).save(any(Appointment.class));
    }

    @Test
    void createAppointment_ThrowsException_WhenServiceNotOfferedByEmployee() {
        // Arrange
        ServiceEntity otherService = new ServiceEntity();
        otherService.setId(99L);
        otherService.setName("Falscher Service");

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(employeeRepo.findById(5L)).thenReturn(Optional.of(employee));
        when(serviceRepo.findById(99L)).thenReturn(Optional.of(otherService));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.createAppointment(1L, 5L, 99L, testDate);
        });

        assertEquals("Mitarbeiter bietet diesen Service nicht an", exception.getMessage());
        verify(repo, never()).save(any());
    }
}