package com.sam.testsecurityrest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.testsecurityrest.Error.Error;
import com.sam.testsecurityrest.Error.LoginException;
import com.sam.testsecurityrest.Model.User;
import com.sam.testsecurityrest.Repository.UserRepository;
import com.sam.testsecurityrest.Service.JwtToken;
import com.sam.testsecurityrest.Service.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController

public class LoginController {


    private UserRepository userRepository;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("api/public/register")
    public User insertUser(@RequestBody User user) {

        log.info(user.getName());
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(4)));
        user.setId(UUID.randomUUID().toString());
        user.setKey(UUID.randomUUID().toString());
        return userRepository.insert(user);
    }


    @GetMapping("api/public/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @GetMapping("api/login")
    public Token login() throws LoginException {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                boolean admin = false;
                if ((user.getRoles().get(0)).equals(User.Role.ADMIN))
                    admin = true;
                Date expirationTime = new Date(System.currentTimeMillis() + 40000000);
                String t = Jwts.builder().claim("username", user.getUsername())
                        .setExpiration(expirationTime)
                        .claim("name", user.getName())
                        .claim("id", user.getId())
                        .claim("admin", admin)
                        .signWith(SignatureAlgorithm.HS256,
                                user.getKey().getBytes("UTF-8")).compact();
                log.info("expirationTime: " + expirationTime);
                Token token = new Token(t);
                return token;

            } else
                throw new LoginException("login error");

        } catch (Exception e) {
            throw new LoginException("login error");
        }
    }


    @PostMapping("api/public/check_token")
    public User verifyToken(@RequestBody Token t) throws Exception {

        String token = t.getToken();
        log.info("Dentro check token: " + token);
        String[] listToken = token.split("\\.");
        String body = listToken[1];
        byte[] decodedBytes = Base64.getDecoder().decode(body);
        String result = new String(decodedBytes);
        ObjectMapper mapper = new ObjectMapper();
        JwtToken jwtToken = mapper.readValue(result, JwtToken.class);
        log.info(jwtToken.getId());
        User user = userRepository.findUsersById(jwtToken.getId());

        Jwts.parser().setSigningKey(user.getKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
        log.info("here i am");
        return userRepository.findUsersByUsername(user.getUsername()).get();
    }


    @ExceptionHandler
    public ResponseEntity<Error> printError(LoginException e) {
        Error error = Error.builder().timestamp(System.currentTimeMillis()).status(404).message(e.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}



