package com.example.terminsystem.service;

import com.example.terminsystem.model.Customer;
import com.example.terminsystem.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repo;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void updateCustomer_Success() {
        // Arrange
        Customer existing = new Customer(1L, "Max", "Mustermann", "max@test.de", "123", "pw");
        Customer updatedData = new Customer(null, "Maxine", "Muster", "maxine@test.de", "456", "new-pw");

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Customer result = customerService.update(1L, updatedData);

        // Assert
        assertEquals("Maxine", result.getFirstName());
        assertEquals("456", result.getPhone());
        verify(repo).save(existing);
    }
}