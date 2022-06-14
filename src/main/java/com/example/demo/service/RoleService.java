package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    public List<Role> findAppUserRole(Long id) {
        return roleRepository.findByAppUserId(id);
    }
    public void addRoles(List<Role> roles){
        roleRepository.saveAll(roles);
    }

    public void addRolesToUser(String userId, List<Role> roles) {

    }
}
