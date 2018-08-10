package com.sam.dishes.Service;

import com.sam.dishes.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Provider extends AbstractUserDetailsAuthenticationProvider {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        log.info("qua");
        String token = usernamePasswordAuthenticationToken.getCredentials().toString();
        log.info("qua");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Token> request = new HttpEntity<>(new Token(token));

        User userResponseEntity = restTemplate.postForObject("http://localhost:9000/api/public/check_token", request, User.class);
        log.info("stampa il nome: " + userResponseEntity.getName());
        return userResponseEntity;

    }
}
