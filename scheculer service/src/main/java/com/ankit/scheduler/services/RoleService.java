package com.ankit.scheduler.services;

import com.ankit.scheduler.Entity.Role;
import com.ankit.scheduler.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public String getRoleType(long id) {
        return this.roleRepository.findById(id).orElseThrow().getType();
    }

    public Role getUserRole() {
        return this.roleRepository.findByType("USER").orElseThrow();
    }

    public Role getAdminRole() {
        return this.roleRepository.findByType("ADMIN").orElseThrow();
    }
}
