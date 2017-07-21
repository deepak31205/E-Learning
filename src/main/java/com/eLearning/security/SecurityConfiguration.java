package com.eLearning.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.eLearning.configuration.DatabaseConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Autowired
	private DatabaseConfig databaseConfig;

	/*Checking the credentials through database using jdbc authentication*/
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(databaseConfig.getDataSource())
		.usersByUsernameQuery("select username,password,enabled from useraccount where username=?")
		.authoritiesByUsernameQuery("select username, authority from useraccount where username=?");
	}  
	
	/*Intercepting all the URL and checking the correct authority accessing the correct url*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/h2-console").hasAnyAuthority("ADMIN")
        	.and()
        	.authorizeRequests()
            .antMatchers("/course/getCourseById","/course/getAllCourses","/course/getTotalCourses", "/course/getUserRole").hasAnyAuthority("USER","ADMIN")
            .and()
            .authorizeRequests()
            .antMatchers("/course/**").hasAnyAuthority("ADMIN")
            .anyRequest().authenticated()
            .and().httpBasic()
    		.authenticationEntryPoint(authEntryPoint);
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}