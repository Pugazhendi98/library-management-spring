package com.example.project.Library_management_system;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	DataSource datasource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
	          auth.jdbcAuthentication()
	               .dataSource(datasource)
	               .usersByUsernameQuery("select email_id,userpassword,active from user where email_id =?")
	               .authoritiesByUsernameQuery("select email_id,role from user where email_id =?");
	               
	
	}


	@Override
    protected void configure(HttpSecurity security) throws Exception
    {
		
		
		security.httpBasic().disable();
		
		security.csrf().disable();
     
    };
	
	
	@Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(10);
	}
}
