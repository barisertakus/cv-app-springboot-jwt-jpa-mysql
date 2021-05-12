package com.bariser.cvapp.auth;

import com.bariser.cvapp.service.Impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;
    private final UserDetailsServiceImpl userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    public JwtTokenFilter(TokenManager tokenManager, UserDetailsServiceImpl userDetailsService) {
        this.tokenManager = tokenManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {

            final String authHeader = httpServletRequest.getHeader("Authorization");

            String username = null;
            String token = null;

            if (authHeader != null && authHeader.contains("Bearer")) {
                token = authHeader.substring(7);

                username = tokenManager.getUsernameToken(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (tokenManager.tokenValidate(token)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken userPasswordToken =
                            new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    userPasswordToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(userPasswordToken);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication : {}", e.getMessage(), e);
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
