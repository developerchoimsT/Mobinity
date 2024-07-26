package com.ms.hoopi.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@ComponentScan
@EnableWebSecurity
public class MySecurityConfiguration {

    @Bean
    public SecurityFilterChain MySecurityfilterChain(HttpSecurity http, MyCorsConfig myCorsConfig) throws Exception {
        log.info("시큐리티작동중?");
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**", "api/hoopi/**").permitAll()
                .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin.disable())
            .logout(logout -> {
                                    logout.logoutUrl("/api/hoopi/logout");
                                    logout.logoutSuccessUrl("/");
                                });
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}