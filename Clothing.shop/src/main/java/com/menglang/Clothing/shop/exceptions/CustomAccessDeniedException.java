package com.menglang.Clothing.shop.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomAccessDeniedException implements AccessDeniedHandler, Serializable {

    private static final long serialVersionUID = 1L;
    @Builder.Default
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        CustomMessageException customMessageException = CustomMessageException.builder()
                .message("The request can't access the resources.")
                .code(String.valueOf(HttpStatus.FORBIDDEN.value())).build();

        var msgJson= mapper.writeValueAsString(customMessageException);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(msgJson);
    }
}
