package com.arnab.springcloud.security.config;

import java.security.KeyPair;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "couponservice";

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource datasource;

	@Value("${keyfile}")
	private String keyfile;

	@Value("${password}")
	private String password;

	@Value("${alias}")
	private String alias;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		//For using in memory token
		/*
		 * endpoints.tokenStore(new InMemoryTokenStore())
		 * .authenticationManager(authenticationManager)
		 * .userDetailsService(userDetailsService);
		 */
		
		//For using DB
		/*
		 * endpoints.tokenStore(new JdbcTokenStore(datasource))
		 * .authenticationManager(authenticationManager)
		 * .userDetailsService(userDetailsService);
		 */
		
		//For JWT
		endpoints.tokenStore(tokenStore()).accessTokenConverter(jwtAccessTokenConverter())
		.authenticationManager(authenticationManager)
		.userDetailsService(userDetailsService);
		
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		//For usng in memory token
		
		  clients.inMemory().withClient("couponclientapp").secret(passwordEncoder.
		  encode("9999")) .authorizedGrantTypes("password","refresh_token")
		  .scopes("read","write") 
		  .resourceIds(RESOURCE_ID);
		 
		
		//For using Token from DB
		
	}
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keyfile), password.toCharArray());
		KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias);
		jwtAccessTokenConverter.setKeyPair(keyPair);
		return jwtAccessTokenConverter;
	}
}
