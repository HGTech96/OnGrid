package com.hg.dashboard.security;


import com.hg.dashboard.domain.User;
import com.hg.dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,
                          UserRepository userRepo) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // Get authorization header and validate
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header.isEmpty() || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }


        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return !ObjectUtils.isEmpty(header) && header.startsWith("Bearer ");
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = new User(jwtTokenUtil.getUserId(token));//userDetailsProvider.getUserDetails(UUID.fromString(jwtTokenUtil.getSubject(token)));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}