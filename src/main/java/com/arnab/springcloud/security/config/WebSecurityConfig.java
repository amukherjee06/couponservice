package com.arnab.springcloud.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//to allow get for all
		.mvcMatchers(HttpMethod.GET, "/couponapi/coupons","/index","/showGetCoupon","/couponDetails","/getCoupon").hasAnyRole("USER", "ADMIN")
		//to allow generate coupon for admin
		.mvcMatchers(HttpMethod.GET, "/showCreateCoupon","/createCoupon","/createResponse").hasRole("ADMIN")
		.mvcMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER","ADMIN")
				.mvcMatchers(HttpMethod.POST, "/couponapi/coupons","/saveCoupon","/getCoupon").hasRole("ADMIN")
				.mvcMatchers(HttpMethod.POST, "/login","/","/showReg","/registerUser").permitAll()
				.and()
				//to disable csrf----to allow post from POSTMAN
				.csrf().disable()
				.logout().logoutSuccessUrl("/");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
}
