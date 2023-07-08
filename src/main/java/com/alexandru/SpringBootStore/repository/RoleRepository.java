package com.alexandru.SpringBootStore.repository;

import com.alexandru.SpringBootStore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

}
