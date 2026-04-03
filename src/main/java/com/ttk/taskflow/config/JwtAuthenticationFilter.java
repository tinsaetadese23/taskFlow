package com.ttk.taskflow.config;

import com.ttk.taskflow.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final static Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

        private final JwtService jwtService;
        private final CustomUserDetailsService userDetailsService;

        public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
            this.jwtService = jwtService;
            this.userDetailsService = userDetailsService;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain)
                throws ServletException, java.io.IOException {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    String username = jwtService.extractUsername(token);
                    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        if(jwtService.isTokenValid(token, userDetails.getUsername())){
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                } catch (Exception e) {
                    //Catch any exceptions
                    logger.warning("JWT authentication failed: " + e.getMessage());

                }
            }
            filterChain.doFilter(request, response);
        }
}
