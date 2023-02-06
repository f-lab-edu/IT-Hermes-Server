package com.hermes.ithermes.presentation.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermes.ithermes.presentation.dto.error.EntryPointErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String exception = request.getAttribute("exception").toString();

        if(exception.equals(SecurityErrorCode.WRONG_TYPE_TOKEN.getCode())) {
            setResponse(response, exception);
            return;
        }

        if(exception.equals(SecurityErrorCode.EXPIRED_TOKEN.getCode())) {
            setResponse(response, exception);
            return;
        }

        if(exception.equals(null)) {
            setResponse(response, "인증실패");
        }
    }

    private void setResponse(HttpServletResponse response, String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EntryPointErrorResponse entryPointErrorResponse = new EntryPointErrorResponse();
        entryPointErrorResponse.setMsg(message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));
    }
}
