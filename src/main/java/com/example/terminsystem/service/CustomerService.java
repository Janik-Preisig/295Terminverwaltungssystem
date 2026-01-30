package com.example.terminsystem.service;

import com.example.terminsystem.model.Customer;
import com.example.terminsystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer create(Customer customer) {
        return repo.save(customer);
    }

    public Customer get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Customer nicht gefunden"));
    }

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer update(Long id, Customer updated) {
        Customer customer = get(id);
        customer.setFirstName(updated.getFirstName());
        customer.setLastName(updated.getLastName());
        customer.setEmail(updated.getEmail());
        customer.setPhone(updated.getPhone());
        customer.setPassword(updated.getPassword());
        return repo.save(customer);
    }
}
