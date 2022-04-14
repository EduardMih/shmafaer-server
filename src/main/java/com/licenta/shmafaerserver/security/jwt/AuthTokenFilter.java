package com.licenta.shmafaerserver.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor @Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Qualifier("userDetailsServiceImpl") private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken;
        UserDetails userDetails;
        UsernamePasswordAuthenticationToken authenticationToken;
        try
        {
            jwtToken = parseJwt(request);

            if((jwtToken != null) && (jwtUtils.validateJwtToken(jwtToken)))
            {
                userDetails = userDetailsService.loadUserByUsername(
                        jwtUtils.getUsernameFromJWTToken(jwtToken)
                );

                authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
            catch (Exception e)
            {
                log.error("Error authenticating user {}", e);
            }

        filterChain.doFilter(request, response);



    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
        {

            return headerAuth.substring(7, headerAuth.length());

        }

        return null;

    }
}
