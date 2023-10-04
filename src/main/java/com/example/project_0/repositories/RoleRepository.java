package com.example.project_0.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.project_0.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
