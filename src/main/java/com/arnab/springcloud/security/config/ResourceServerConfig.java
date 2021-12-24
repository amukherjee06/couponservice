package com.arnab.springcloud.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "couponservice";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID);
	}
	//-------Used to reside in WebSecur
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//to allow get for all
		.mvcMatchers(HttpMethod.GET, "/couponapi/coupons")
		.hasAnyRole("USER", "ADMIN")
				.mvcMatchers(HttpMethod.POST, "/couponapi/coupons").hasRole("ADMIN")
				.and()
				//to disable csrf----to allow post from POSTMAN
				.csrf().disable();
	}

}
