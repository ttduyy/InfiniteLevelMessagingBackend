package com.example.infinite_level_messaging_system.JWT;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Lấy token từ cookie
        String token;
        Cookie cookie = WebUtils.getCookie(request, "JWT_TOKEN");
        String authHeader = request.getHeader("Authorization");
        HttpSession session = request.getSession();


        if (cookie != null) { //Check cookie
            token = cookie.getValue();
        }
        else if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        else if (session != null) {
            // Lấy JWT từ session
            String sessionAttribute = (String) session.getAttribute("JWT_TOKEN");

            if (sessionAttribute != null && !sessionAttribute.isEmpty()) {
                token = sessionAttribute;
            }
            else {
                filterChain.doFilter(request, response);
                return;
            }
        }
        else {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = token;

        System.out.println(jwt);
        String username = jwtService.extractUsername(jwt);
        if (username == null){ // jwt is expired or can not find username
            assert cookie != null;
            cookie.setMaxAge(0); // This will delete the cookie
            cookie.setPath("/"); // Set the same path as the original cookie
            response.addCookie(cookie);
        }
        System.out.println(username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else {
                // Token is invalid or expired, delete the cookie
                Cookie expiredCookie = new Cookie("JWT_TOKEN", null);
                expiredCookie.setMaxAge(0); // This will delete the cookie
                expiredCookie.setPath("/"); // Set the same path as the original cookie
                response.addCookie(expiredCookie);

                filterChain.doFilter(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);

    }
}

