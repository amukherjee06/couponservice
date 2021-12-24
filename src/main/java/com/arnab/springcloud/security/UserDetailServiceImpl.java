package com.arnab.springcloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arnab.springcloud.model.User;
import com.arnab.springcloud.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("User name with email "+username+" not found");
		}
		return new org.springframework.security.core.userdetails
				.User(user.getEmail(), user.getPassword(),user.getRoles());
	}

}
