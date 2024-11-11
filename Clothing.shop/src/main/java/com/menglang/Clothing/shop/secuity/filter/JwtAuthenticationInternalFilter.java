package com.menglang.Clothing.shop.secuity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menglang.Clothing.shop.configs.JwtConfig;
import com.menglang.Clothing.shop.exceptions.CustomMessageExceptionUtils;
import com.menglang.Clothing.shop.secuity.jwt.JwtService;
import com.menglang.Clothing.shop.secuity.userDetails.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationInternalFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final JwtConfig jwtConfig;
    private final CustomUserDetailService userDetailService;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> excludedPaths = Arrays.asList(
                "/api/v1/public/accounts/login",
                "/api/v1/public/accounts/register",
                "/h2-console/**"
                // Add other permitAll endpoints or patterns here
        );

        AntPathMatcher pathMatcher = new AntPathMatcher();
        String requestPath = request.getServletPath();

        return excludedPaths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestPath));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        var accessToken = request.getHeader(jwtConfig.getHeader());
        log.info("Processing request for URI: {}", request.getRequestURI());

        try {
            // Check if token is missing, empty, or does not start with the required prefix
            if (accessToken == null || accessToken.isBlank()) {
                log.error("Missing or invalid token");
                sendErrorResponse(response, "Token is missing or invalid");
                return; // Stop further execution when token is invalid
            }
            if (!accessToken.startsWith(jwtConfig.getPrefix())) {
                log.error("Token does not have the required prefix");
                sendErrorResponse(response, "Invalid token prefix. Expected '" + jwtConfig.getPrefix() + "'.");
                return;
            }
            // Remove the prefix from the token
            accessToken = accessToken.substring(jwtConfig.getPrefix().length());

            // Validate the token
            if (jwtService.isValidToken(accessToken)) {
                Claims claims = jwtService.extractClaims(accessToken);
                var username = claims.getSubject();

                if (username != null) {
                    // Load user details from the database
                    UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

                    // Set authentication in security context
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } else {
                log.error("Invalid token");
                sendErrorResponse(response, "Invalid token");
                return; // Stop further execution when token is invalid
            }

            // Proceed with the filter chain
            log.info("Proceeding with filter chain for URI: {}", request.getRequestURI());
            filterChain.doFilter(request, response);

        } catch (RuntimeException e) {
            log.error("Error processing request: {}", e.getMessage());
            sendErrorResponse(response, e.getLocalizedMessage());
        }
    }

    // Helper method to send an error response with custom message
    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        var messageException = CustomMessageExceptionUtils.unauthorized(errorMessage);
        var msgJson = objectMapper.writeValueAsString(messageException);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(msgJson);
        response.getWriter().flush();
    }
}
