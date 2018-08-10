package com.sam.menu.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthTokenFilter extends AbstractAuthenticationProcessingFilter {

    private Logger log = LoggerFactory.getLogger(getClass());

    public AuthTokenFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String[] authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).split(" ");
        String token = authorization[1];
        log.info("token: " + token);

        // chiamata a endpoint verifica token.

        Authentication authentication = new UsernamePasswordAuthenticationToken("username", token);
        return getAuthenticationManager().authenticate(authentication);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        log.info("chainfilter");
        chain.doFilter(request, response);
    }

}
