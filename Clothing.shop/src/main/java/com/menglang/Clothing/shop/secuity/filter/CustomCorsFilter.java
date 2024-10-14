package com.menglang.Clothing.shop.secuity.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;


import java.io.IOException;

@Configuration
@Slf4j
public class CustomCorsFilter implements Filter {

    private final static String ACCESS_CONTROL_ALLOW_ORIGIN="Access-Control-Allow-Origin";
    private final static String ACCESS_CONTROL_ALLOW_METHOD ="Access-Control-Allow-Methods";
    private final static String ACCESS_CONTROL_ALLOW_HEADER="Access-Control-Allow-Headers";
    private final static String ACCESS_CONTROL_MAX_AGE="Access-Control-Max-Age";

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        log.info("servlet Request {}","*");
        log.info("Servlet Response {}","*");

        final HttpServletResponse httpServletResponse= (HttpServletResponse) response;

        httpServletResponse.setHeader(ACCESS_CONTROL_ALLOW_HEADER,"*");
        httpServletResponse.setHeader(ACCESS_CONTROL_ALLOW_METHOD,"*");
        httpServletResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        httpServletResponse.setHeader(ACCESS_CONTROL_MAX_AGE,"36000");

        if(HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) request).getMethod())){
           ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
        }
        log.info("----------------Servlet http method name: {}",HttpMethod.OPTIONS.name());
        filterChain.doFilter(request,response);
    }
}
