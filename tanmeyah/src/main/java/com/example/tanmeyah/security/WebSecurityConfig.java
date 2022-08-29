package com.example.tanmeyah.security;

import com.example.tanmeyah.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.example.tanmeyah.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .csrf().disable().authorizeRequests()
                .antMatchers("/tanmeyah/branch/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "tanmeyah/customer").permitAll()
                .antMatchers(HttpMethod.POST, "/tanmeyah/customer")
                .hasAnyRole(Role.LOAN_OFFICER.name(), Role.TELLER.name())
                .antMatchers(HttpMethod.PUT, "/tanmeyah/customer").hasRole(Role.TELLER.name())
                .antMatchers("loan/confirm").hasRole(Role.MANAGER.name())
                .antMatchers("loan/revise").hasRole(Role.REVISOR.name())
                .antMatchers("loan/view").hasRole(Role.REVISOR.name())
                .antMatchers("/tanmeyah/registration/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(employeeService);
        return provider;
    }


}
