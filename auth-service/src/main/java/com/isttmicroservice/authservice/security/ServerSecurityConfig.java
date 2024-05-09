package com.isttmicroservice.authservice.security;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.isttmicroservice.authservice.JwtTokenFilter;

//import com.isttmicroservice.authservice.JwtTokenFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class ServerSecurityConfig {
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123")).roles("USER").and().passwordEncoder(passwordEncoder());
		
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Value("${cors.origin.patterns}")
	private List<String> origins;

	@Value("${cors.methods}")
	private List<String> methods;

	@Value("${cors.headers}")
	private List<String> headers;

	// cau hinh phan quyen api
//	@Override
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.cors().configurationSource(new CorsConfigurationSource() {

					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration configuration = new CorsConfiguration();
						configuration.setAllowedOrigins(Arrays.asList("*"));
						configuration.setAllowedMethods(Arrays.asList("*"));
						configuration.setAllowedHeaders(Arrays.asList("*"));
						return configuration;
					}
					
				}).and().csrf().disable().authorizeRequests().antMatchers("/api/auth/**").permitAll()
				.antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
//	    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		http.authorizeRequests().antMatchers("/api/auth/**").permitAll().anyRequest().authenticated().and().formLogin();
//	}
//
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception{
//		return super.authenticationManagerBean();
//	}

}
