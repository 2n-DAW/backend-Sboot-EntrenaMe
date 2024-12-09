package com.springboot.entrename.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.entrename.domain.exception.Error;
import com.springboot.entrename.domain.exception.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        AppException appException = new AppException(Error.UNAUTHORIZED);

        ErrorMessages errorMessages = new ErrorMessages();
        errorMessages.addError(appException.getError().name(), appException.getMessage());

        response.setStatus(appException.getError().getStatus().value());
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(response.getWriter(), errorMessages);
    }
}
