package com.example.terminsystem.service;

import com.example.terminsystem.model.ServiceEntity;
import com.example.terminsystem.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {
    private final ServiceRepository repo;

    public ServiceService(ServiceRepository repo) {
        this.repo = repo;
    }

    public ServiceEntity create(ServiceEntity service) {
        return repo.save(service);
    }

    public ServiceEntity get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public List<ServiceEntity> getAll() {
        return repo.findAll();
    }
}
