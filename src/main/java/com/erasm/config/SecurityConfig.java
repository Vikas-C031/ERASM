package com.erasm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.erasm.security.CustomUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.erasm.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomUserDetailsService customUserDetailsService;
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,CustomUserDetailsService customUserDetailsService) {
	    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    this.customUserDetailsService = customUserDetailsService;
	}
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                	    // Public APIs
                	    .requestMatchers("/auth/login","/auth/register")
                	    .permitAll()

                	    // -------------------------
                	    // ROLE MANAGEMENT
                	    // -------------------------
                	    .requestMatchers(HttpMethod.GET, "/roles/**")
                	    .hasRole("ADMIN")

                	    .requestMatchers(HttpMethod.POST, "/roles/**")
                	    .hasRole("ADMIN")

                	    .requestMatchers(HttpMethod.PUT, "/roles/**")
                	    .hasRole("ADMIN")

                	    .requestMatchers(HttpMethod.DELETE, "/roles/**")
                	    .hasRole("ADMIN")

                	    // -------------------------
                	    // USER MANAGEMENT
                	    // -------------------------
                	    .requestMatchers(HttpMethod.GET, "/users/**")
                	    .hasRole("ADMIN")

                	    .requestMatchers(HttpMethod.POST, "/users/**")
                	    .hasRole("ADMIN")

                	    .requestMatchers(HttpMethod.PUT, "/users/**")
                	    .hasRole("ADMIN")

                	    .requestMatchers(HttpMethod.DELETE, "/users/**")
                	    .hasRole("ADMIN")

                	    // -------------------------
                	    // EMPLOYEE MANAGEMENT
                	    // -------------------------
                	    .requestMatchers(HttpMethod.GET, "/employees/**")
                	    .hasAnyRole(
                	            "ADMIN",
                	            "DELIVERY_MANAGER",
                	            "RESOURCE_MANAGER")

                	    .requestMatchers(HttpMethod.POST, "/employees/**")
                	    .hasRole("ADMIN")

                	    .requestMatchers(HttpMethod.PUT, "/employees/**")
                	    .hasRole("ADMIN")

                	    .requestMatchers(HttpMethod.DELETE, "/employees/**")
                	    .hasRole("ADMIN")
                	    
	                	 // -------------------------
	                	 // SKILL MANAGEMENT
	                	 // -------------------------
	
	                	 .requestMatchers(HttpMethod.GET, "/skills/**")
	                	 .hasAnyRole(
	                	         "ADMIN",
	                	         "DELIVERY_MANAGER",
	                	         "RESOURCE_MANAGER",
	                	         "EMPLOYEE")
	
	                	 .requestMatchers(HttpMethod.POST, "/skills/**")
	                	 .hasRole("ADMIN")
	
	                	 .requestMatchers(HttpMethod.PUT, "/skills/**")
	                	 .hasRole("ADMIN")
	
	                	 .requestMatchers(HttpMethod.DELETE, "/skills/**")
	                	 .hasRole("ADMIN")
                	 
	                	// -------------------------
	                	// EMPLOYEE SKILLS
	                	// -------------------------

	                	.requestMatchers(HttpMethod.GET, "/employee-skills/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "DELIVERY_MANAGER",
	                	        "RESOURCE_MANAGER",
	                	        "EMPLOYEE")

	                	.requestMatchers(HttpMethod.POST, "/employee-skills/**")
	                	.hasRole("ADMIN")

	                	.requestMatchers(HttpMethod.PUT, "/employee-skills/**")
	                	.hasRole("ADMIN")

	                	.requestMatchers(HttpMethod.DELETE, "/employee-skills/**")
	                	.hasRole("ADMIN")
	                	
	                	// -------------------------
	                	// CERTIFICATIONS
	                	// -------------------------

	                	.requestMatchers(HttpMethod.GET, "/certifications/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "DELIVERY_MANAGER",
	                	        "RESOURCE_MANAGER",
	                	        "EMPLOYEE")

	                	.requestMatchers(HttpMethod.POST, "/certifications/**")
	                	.hasRole("ADMIN")

	                	.requestMatchers(HttpMethod.PUT, "/certifications/**")
	                	.hasRole("ADMIN")

	                	.requestMatchers(HttpMethod.DELETE, "/certifications/**")
	                	.hasRole("ADMIN")
	                	
	                	// -------------------------
	                	// PROJECT MANAGEMENT
	                	// -------------------------

	                	.requestMatchers(HttpMethod.GET, "/projects/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "DELIVERY_MANAGER",
	                	        "RESOURCE_MANAGER")

	                	.requestMatchers(HttpMethod.POST, "/projects/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "DELIVERY_MANAGER")

	                	.requestMatchers(HttpMethod.PUT, "/projects/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "DELIVERY_MANAGER")

	                	.requestMatchers(HttpMethod.DELETE, "/projects/**")
	                	.hasRole("ADMIN")
	                	
	                	// -------------------------
	                	// RESOURCE REQUESTS
	                	// -------------------------

	                	.requestMatchers(HttpMethod.GET, "/resource-requests/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "DELIVERY_MANAGER",
	                	        "RESOURCE_MANAGER")

	                	.requestMatchers(HttpMethod.POST, "/resource-requests/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "DELIVERY_MANAGER")

	                	.requestMatchers(HttpMethod.PUT, "/resource-requests/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "RESOURCE_MANAGER")

	                	.requestMatchers(HttpMethod.DELETE, "/resource-requests/**")
	                	.hasRole("ADMIN")
	                	
	                	// -------------------------
	                	// ALLOCATIONS
	                	// -------------------------

	                	.requestMatchers(HttpMethod.GET, "/allocations/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "DELIVERY_MANAGER",
	                	        "RESOURCE_MANAGER")

	                	.requestMatchers(HttpMethod.POST, "/allocations/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "RESOURCE_MANAGER")

	                	.requestMatchers(HttpMethod.PUT, "/allocations/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "RESOURCE_MANAGER")

	                	.requestMatchers(HttpMethod.DELETE, "/allocations/**")
	                	.hasRole("ADMIN")
	                	
	                	// -------------------------
	                	// DASHBOARD
	                	// -------------------------

	                	.requestMatchers(HttpMethod.GET, "/dashboard/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "DELIVERY_MANAGER",
	                	        "RESOURCE_MANAGER")
	                	
	                	// -------------------------
	                	// AUDIT LOGS
	                	// -------------------------

	                	.requestMatchers(HttpMethod.GET, "/audit-logs/**")
	                	.hasAnyRole(
	                	        "ADMIN",
	                	        "AUDITOR")

                	    .anyRequest()
                	    .authenticated()
                	);
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

}