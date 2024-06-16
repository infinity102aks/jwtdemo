package com.ankit.scheduler.security;


import com.ankit.scheduler.services.CustomUserDetails;
import com.ankit.scheduler.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilterChain extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.getToken(request);
        if(!request.getRequestURI().startsWith("/api/auth") && StringUtils.hasText(token) && jwtTokenUtil.validateToken(token)) {
            String userName = jwtTokenUtil.getUserNameFromToken(token);
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            UserContextHolder.setUserDetail(userDetails);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.clearContext();
        }
    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie bearerCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("Bearer")).findFirst().orElse(null);
            if (bearerCookie != null) {
                return bearerCookie.getValue();
            }
        }

        return null;
    }
}
