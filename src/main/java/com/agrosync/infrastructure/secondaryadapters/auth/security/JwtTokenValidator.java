package com.agrosync.infrastructure.secondaryadapters.auth.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.agrosync.crosscutting.helpers.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;

    public JwtTokenValidator(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtToken = jwtToken.substring(7);
            DecodedJWT decodedJWT = jwtHelper.validateToken(jwtToken);

            String userName = jwtHelper.extractUserName(decodedJWT);
            String stringAuthorities = jwtHelper.getSpecificClaim(decodedJWT, "autorities").asString();

            Collection<GrantedAuthority> authorities =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userName, null, authorities);

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);

        } catch (com.auth0.jwt.exceptions.JWTVerificationException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = """
                {
                    "mensajes": ["Token inválido, no autorizado"]
                }
                """;

            response.getWriter().write(jsonResponse);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
