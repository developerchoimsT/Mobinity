package com.ms.hoopi.config;

import com.ms.hoopi.filter.JwtFilter;
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
public class SecurityConfig {

    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain MySecurityfilterChain(HttpSecurity http) throws Exception {
        log.info("시큐리티작동중?");
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("hoopi/login", "hoopi/join", "hoopi/email", "hoopi/phone").permitAll()
                .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin.disable())
            .logout(logout -> logout.disable())
            .addFilter(jwtFilter);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}