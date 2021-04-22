package com.example.fake_amazon_API.controllers;

import com.example.fake_amazon_API.CustomizedResponse;
import com.example.fake_amazon_API.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
@CrossOrigin(origins="*")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value="/auth",consumes={
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity login(@RequestBody UserModel user) {
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication token = authenticationManager.authenticate(request);
            var response = new CustomizedResponse("You login in successfully", null);
            return new ResponseEntity(token, HttpStatus.OK);
        } catch(BadCredentialsException ex){
            var response = new CustomizedResponse("Your username and/or password were entered incorrectly", null);
            return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
        }

    }
}
