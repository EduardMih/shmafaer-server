package com.licenta.shmafaerserver.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component @Slf4j
public class AuthEntryPointJwt  implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Map<String, String> responseMes = new HashMap<>();
        log.error("Unauthorized error: {}", authException.getMessage());
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        responseMes.put("Error", "Bad credentials");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(response.getOutputStream(), responseMes);
    }
}
