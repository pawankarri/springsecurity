package com.spring.practice.config;

import com.spring.practice.service.UserInfoUserDetailsService;
import com.spring.practice.serviceImpl.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtServiceImpl jwtServiceImpl;
    @Autowired
    private UserInfoUserDetailsService userInfoUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String username=null;
        if (authHeader!=null&&authHeader.startsWith("Bearer "))
        {
           token=authHeader.substring(7);
           username=jwtServiceImpl.getUserNameFromToken(token);
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=userInfoUserDetailsService.loadUserByUsername(username);
            if (jwtServiceImpl.validateToken(token,userDetails))
            {
                UsernamePasswordAuthenticationToken authenticationFilter=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationFilter.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationFilter);
            }
        }
        filterChain.doFilter(request,response);
    }
}
