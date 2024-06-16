package com.ankit.scheduler.security;

import com.ankit.scheduler.models.AuthResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    JwtFilterChain jwtFilterChain;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
                .authorizeHttpRequests(
                    auth -> auth.requestMatchers("/api/auth/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(this.jwtFilterChain, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public static void createSecurityCookie(HttpServletResponse response, AuthResponseDTO dto) throws IOException {
        ResponseCookie cookie = ResponseCookie.from("Bearer", dto.getToken())
                .httpOnly(true)
                .path("/")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
