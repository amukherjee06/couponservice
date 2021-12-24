package com.arnab.springcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arnab.springcloud.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
