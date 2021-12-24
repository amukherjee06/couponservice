package com.arnab.springcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arnab.springcloud.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
}
